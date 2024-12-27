package junseok.snr.ecommerce.order.application.query

import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult

interface OrderQueryService {
    fun getOrderById(query: GetOrderByIdQuery): GetOrderByIdResult
    fun getOrdersByUserId(query: GetOrdersByUserIdQuery): GetOrdersByUserIdResult
}