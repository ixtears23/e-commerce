package junseok.snr.ecommerce.core.order.api.request

import java.math.BigDecimal

// 주문 생성 요청 DTO
data class CreateOrderRequest(
    val userId: Long,
    val orderItems: List<CreateOrderItemRequest>
) {
    data class CreateOrderItemRequest(
        val productId: Long,
        val quantity: Int,
        val price: BigDecimal
    )
}
