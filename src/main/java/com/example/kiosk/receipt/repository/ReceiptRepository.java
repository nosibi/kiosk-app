package com.example.kiosk.receipt.repository;

import com.example.kiosk.receipt.entity.Receipt;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
    //지정된 범위의 시간 중 대기번호가 가장 큰 값을 가진 영수증, 매개변수는 Between에 적용
    Optional<Receipt> findTopByCreatedAtBetweenOrderByWaitingNumberDesc(
            LocalDateTime startOfDay, LocalDateTime endOfDay);

    //주문 번호로 영수증 조회
    Optional<Receipt> findReceiptByOrder_Id(Long orderId);

    //특정 기간(또는 시간)동안의 영수증 목록 조회
    List<Receipt> findAllByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
