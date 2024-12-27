package junseok.snr.ecommerce.order.entity

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("orders")
data class OrderTable(
    @Id
    var orderId: Long = 0,
    var userId: Long,
    var orderDate: LocalDateTime = LocalDateTime.now(),
    var status: String = OrderStatus.PENDING.name, // String 타입으로 변경
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    @Transient
    var orderItems: List<OrderItemTable> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
    @Transient
    var new: Boolean = true

) : Persistable<Long> {

    override fun getId(): Long? = orderId

    override fun isNew(): Boolean = new

    fun updateStatus(newStatus: OrderStatus) {
        this.status = newStatus.name
        this.updatedAt = LocalDateTime.now()
    }
}