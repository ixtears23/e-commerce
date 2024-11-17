package junseok.snr.ecommerce.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class OrderEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long = 0L,
    var userId: Long,
    var orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
)

enum class OrderStatus(val description: String) {
    PENDING("결제 대기 중"),
    PAID("결제 완료"),
    SHIPPED("배송 중"),
    COMPLETED("거래 완료"),
    CANCELLED("주문 취소")
}