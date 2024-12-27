package junseok.snr.ecommerce.order.application.query

import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult
import reactor.core.publisher.Mono

interface OrderQueryService {
    fun getOrderById(query: GetOrderByIdQuery): Mono<GetOrderByIdResult>
    fun getOrdersByUserId(query: GetOrdersByUserIdQuery): Mono<GetOrdersByUserIdResult>
}