package junseok.snr.ecommerce.core.order.api.dto

import java.math.BigDecimal

data class OrderItemApiDto(
    val orderItemId: Long = 0L,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal
)
