package junseok.snr.ecommerce.core.order.application.dto

import java.math.BigDecimal

data class OrderItemDto(
    val orderItemId: Long = 0L,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal
)
