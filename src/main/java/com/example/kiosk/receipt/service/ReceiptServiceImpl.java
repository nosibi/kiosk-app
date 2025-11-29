package com.example.kiosk.receipt.service;

import com.example.kiosk.order.dto.OrderItemResponseDTO;
import com.example.kiosk.order.entity.Order;
import com.example.kiosk.order.entity.OrderItem;
import com.example.kiosk.receipt.dto.ReceiptResponseDTO;
import com.example.kiosk.receipt.entity.Receipt;
import com.example.kiosk.order.exception.OrderNotFoundException;
import com.example.kiosk.receipt.exception.ReceiptNotFoundException;
import com.example.kiosk.order.repository.OrderRepository;
import com.example.kiosk.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public ReceiptResponseDTO findReceipt(Long orderId) {
        ReceiptResponseDTO receiptResponseDTO = new ReceiptResponseDTO();
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Receipt receipt = receiptRepository.findReceiptByOrder_Id(order.getId()).orElseThrow(ReceiptNotFoundException::new);

        List<OrderItemResponseDTO> orderItemResponseDTOList = new ArrayList<>();

        for(OrderItem orderItem : order.getOrderItemList()){
            OrderItemResponseDTO  orderItemResponseDTO = new OrderItemResponseDTO();
            orderItemResponseDTO.setItemName(orderItem.getItemName());
            orderItemResponseDTO.setItemPrice(orderItem.getItemPrice());
            orderItemResponseDTO.setQuantity(orderItem.getQuantity());
            orderItemResponseDTO.setAddSugar(orderItem.isAddSugar());
            orderItemResponseDTO.setAddShot(orderItem.isAddShot());
            orderItemResponseDTO.setAddIce(orderItem.isAddIce());
            orderItemResponseDTOList.add(orderItemResponseDTO);
        }

        receiptResponseDTO.setItems(orderItemResponseDTOList);
        receiptResponseDTO.setWaitingNumber(receipt.getWaitingNumber());
        receiptResponseDTO.setCost(receipt.getCost());
        return receiptResponseDTO;
    }

    @Override
    public String getQueueTicket(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Receipt receipt = receiptRepository.findReceiptByOrder_Id(order.getId()).orElseThrow(ReceiptNotFoundException::new);
        return "대기 번호 : " + receipt.getWaitingNumber();
    }
}