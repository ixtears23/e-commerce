package junseok.snr.ecommerce.core.order.application.command

import junseok.snr.ecommerce.core.order.application.dto.OrderDto

data class UpdateOrderStatusResult(
    val order: OrderDto
)