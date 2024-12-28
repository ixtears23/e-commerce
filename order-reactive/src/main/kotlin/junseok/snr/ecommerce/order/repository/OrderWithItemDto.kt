package junseok.snr.ecommerce.order.repository

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderWithItemDto(
    val orderId: Long,
    val userId: Long,
    val orderDate: LocalDateTime,
    val status: String,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val orderItemId: Long?,
    val productId: Long?,
    val quantity: Int?,
    val price: BigDecimal?
)
