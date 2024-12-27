package junseok.snr.ecommerce.order.entity

import jakarta.persistence.*
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class OrderEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val orderId: Long = 0L,
        var userId: Long,
        var orderDate: LocalDateTime = LocalDateTime.now(),
        @Enumerated(EnumType.STRING)
        var status: OrderStatus = OrderStatus.PENDING,
        var totalAmount: BigDecimal = BigDecimal.ZERO,
        @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
        var orderItems: MutableList<OrderItemEntity> = mutableListOf(),
        val createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime? = null
) {
    fun updateStatus(newStatus: OrderStatus) {
        this.status = newStatus
        this.updatedAt = LocalDateTime.now()
    }
}
