package com.example.kiosk.order.service;

import com.example.kiosk.menu.entity.Menu;
import com.example.kiosk.menu.exception.MenuNotFoundException;
import com.example.kiosk.menu.repository.MenuRepository;
import com.example.kiosk.order.dto.OrderItemDTO;
import com.example.kiosk.order.dto.OrderItemResponseDTO;
import com.example.kiosk.order.dto.OrderResponseDTO;
import com.example.kiosk.order.entity.Order;
import com.example.kiosk.order.entity.OrderItem;
import com.example.kiosk.receipt.entity.Receipt;
import com.example.kiosk.order.exception.OrderNotFoundException;
import com.example.kiosk.order.repository.OrderItemRepository;
import com.example.kiosk.order.repository.OrderRepository;
import com.example.kiosk.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void makeOrder(boolean isTakeOut, List<OrderItemDTO> orderItemDTOs) {
        Order order = new Order();
        order.setTakeOut(isTakeOut);

        Receipt receipt = new Receipt();
        //영수증에 저장될 비용 정보
        Integer cost = 0;

        for(OrderItemDTO dto : orderItemDTOs){
            Menu menu = menuRepository.findById(dto.getItemId())
                    .orElseThrow(MenuNotFoundException::new);

            //DTO를 Entity로 변환
            OrderItem orderItem = OrderItem.builder()
                    .itemName(menu.getName())
                    .itemPrice(menu.getPrice())
                    .quantity(dto.getQuantity())
                    .addIce(dto.isAddIce())
                    .addShot(dto.isAddShot())
                    .addSugar(dto.isAddSugar())
                    .build();

            orderItemRepository.save(orderItem);

            //변환한 Entity를 Order의 OrderItemList에 저장
            order.addOrderItem(orderItem);

            //추가 옵션 있을 시 추가 비용
            Integer additionalCost = 0;

            additionalCost += dto.isAddIce() ? 500 : 0;
            additionalCost += dto.isAddShot() ? 500 : 0;
            additionalCost += dto.isAddSugar() ? 500 : 0;

            cost += dto.getQuantity() * (menu.getPrice() +  additionalCost);
        }
        receipt.setCost(cost);

        //오늘 날짜의 시작 ~ 끝
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); //00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); //23:59:59

        Optional<Receipt> lastReceipt = receiptRepository.findTopByCreatedAtBetweenOrderByWaitingNumberDesc(startOfDay, endOfDay);

        //오늘 00:00부터 23:59까지 대기번호가 가장 큰 영수증 반환(가장 최근에 주문), 없을 경우 오늘 주문이 없었기 때문에 대기번호 1번 부여
        Integer waitingNumber = lastReceipt.map(r -> r.getWaitingNumber() + 1).orElse(1);

        //하루가 지나면 대기번호가 리셋되어야 함
        receipt.setWaitingNumber(waitingNumber);

        receipt.setOrder(order);

        receiptRepository.save(receipt);
        orderRepository.save(order);
    }


    @Override
    public Long findRecentOrderId() {
        Order order = orderRepository.findTopByOrderByIdDesc().orElseThrow(OrderNotFoundException::new);
        return order.getId();
    }

    @Override
    public List<OrderResponseDTO> findAllToday() {

        //오늘 날짜의 시작 ~ 끝
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); //00:00:00
        LocalDateTime endOfDay = today.atTime(23,59,59); //23:59:59

        List<Order> findOrderList = orderRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();

        for (Order order : findOrderList) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrderNumber(order.getReceipt().getWaitingNumber());
            extractOrderItem(order,orderResponseDTO.getOrderItemDTOList());
            orderResponseDTO.setDiscountPrice(1500*order.getReceipt().getUsedCoupon());
            orderResponseDTO.setUsedCoupon(order.getReceipt().getUsedCoupon());
            orderResponseDTO.setCost(order.getReceipt().getCost());
            orderResponseDTO.setTakeOut(order.isTakeOut());

            orderResponseDTOList.add(orderResponseDTO);
        }

        return orderResponseDTOList;
    }


    private void extractOrderItem(Order order, List<OrderItemResponseDTO> orderItemResponseDTOList) {
        order.getOrderItemList().forEach(orderItem -> {
            OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
            orderItemResponseDTO.setItemName(orderItem.getItemName());
            orderItemResponseDTO.setItemPrice(orderItem.getItemPrice());
            orderItemResponseDTO.setQuantity(orderItem.getQuantity());
            orderItemResponseDTO.setAddIce(orderItem.isAddIce());
            orderItemResponseDTO.setAddShot(orderItem.isAddShot());
            orderItemResponseDTO.setAddSugar(orderItem.isAddSugar());

            orderItemResponseDTOList.add(orderItemResponseDTO);
        });
    }
}
