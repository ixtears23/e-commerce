package junseok.snr.ecommerce.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class OrderEntity (
    @Id
    @GeneratedValue
    val orderId: Long,
    val userId: Long,
    val orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

enum class OrderStatus(val description: String) {
    PENDING("결제 대기 중"),
    PAID("결제 완료"),
    SHIPPED("배송 중"),
    COMPLETED("거래 완료"),
    CANCELLED("주문 취소")
}