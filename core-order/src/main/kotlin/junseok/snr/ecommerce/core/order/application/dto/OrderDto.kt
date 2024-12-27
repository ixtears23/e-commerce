package junseok.snr.ecommerce.core.order.application.dto

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val orderId: Long = 0L,
    var userId: Long,
    var orderDate: LocalDateTime = LocalDateTime.now(),
    var status: OrderStatus = OrderStatus.PENDING,
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    var orderItems: List<OrderItemDto> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
)
