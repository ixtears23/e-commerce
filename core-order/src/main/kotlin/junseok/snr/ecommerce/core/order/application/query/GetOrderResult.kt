package junseok.snr.ecommerce.core.order.application.query

import junseok.snr.ecommerce.core.order.application.dto.OrderDto

data class GetOrderResult(
    val order: OrderDto
)