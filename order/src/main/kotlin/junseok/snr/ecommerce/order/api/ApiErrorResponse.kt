package junseok.snr.ecommerce.order.api

import junseok.snr.ecommerce.order.application.exception.OrderError

data class ApiErrorResponse(
    val code: OrderError,
    val message: String
)
