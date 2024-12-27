package junseok.snr.ecommerce.core.order.api.response

import junseok.snr.ecommerce.core.order.api.dto.OrderApiDto

data class UpdateOrderStatusResponse (
    val order: OrderApiDto
)
