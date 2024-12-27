package junseok.snr.ecommerce.core.order.application.query

import junseok.snr.ecommerce.core.order.application.dto.OrderDto

data class GetOrderByIdResult(
    val order: OrderDto
)