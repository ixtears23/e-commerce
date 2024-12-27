package junseok.snr.ecommerce.core.order.api.dto

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderApiDto(
    val orderId: Long = 0L,
    var userId: Long,
    var orderDate: LocalDateTime = LocalDateTime.now(),
    var status: OrderStatus = OrderStatus.PENDING,
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    var orderItems: List<OrderItemApiDto> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
)
