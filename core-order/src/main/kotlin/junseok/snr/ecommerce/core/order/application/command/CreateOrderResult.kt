package junseok.snr.ecommerce.core.order.application.command

import junseok.snr.ecommerce.core.order.application.dto.OrderDto

data class CreateOrderResult(
    val order: OrderDto
)
