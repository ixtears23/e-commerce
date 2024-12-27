package junseok.snr.ecommerce.core.order.api.response

import junseok.snr.ecommerce.core.order.api.dto.OrderApiDto

// 주문 조회 응답 DTO
data class CreateOrderResponse(
    val order: OrderApiDto
)
