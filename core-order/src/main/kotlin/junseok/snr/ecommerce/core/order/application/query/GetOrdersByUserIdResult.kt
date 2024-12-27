package junseok.snr.ecommerce.core.order.application.query

import junseok.snr.ecommerce.core.order.application.dto.OrderDto

data class GetOrdersByUserIdResult(
    val orders: List<OrderDto>
)