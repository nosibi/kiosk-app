package com.example.kiosk.receipt.entity;

import com.example.global.BasedEntity;
import com.example.kiosk.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receipt extends BasedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cost;

    private Integer waitingNumber;

    private Integer usedCoupon = 0; //0으로 초기화하지 않으면 쿠폰 미사용 시 null이면 주문 내역에서 500 에러 발생

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
        order.setReceipt(this);
    }

    //할인 쿠폰 사용 시 최종 결제 금액에서 할인금액을 차감해야 함
    public void setFinalCost(){
        if(this.usedCoupon!=null){
            this.cost -= 1500 * usedCoupon;
        }
    }
}
