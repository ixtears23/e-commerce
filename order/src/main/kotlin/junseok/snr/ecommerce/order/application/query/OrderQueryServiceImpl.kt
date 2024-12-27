package junseok.snr.ecommerce.order.application.query

import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult
import junseok.snr.ecommerce.order.mapper.toOrderDto
import junseok.snr.ecommerce.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderQueryServiceImpl(
    private val orderRepository: OrderRepository
): OrderQueryService {

    override fun getOrderById(query: GetOrderByIdQuery): GetOrderByIdResult {
        return GetOrderByIdResult(
            orderRepository.findById(query.orderId)
                .orElseThrow { NoSuchElementException("Order not found with id: ${query.orderId}") }
                .toOrderDto()
        )
    }

    override fun getOrdersByUserId(query: GetOrdersByUserIdQuery): GetOrdersByUserIdResult {
        return GetOrdersByUserIdResult(
            orderRepository.findByUserId(query.userId)
                .map { it.toOrderDto() }
                .toMutableList()
        )
    }

}