package junseok.snr.ecommerce.order.application.query

import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult
import junseok.snr.ecommerce.order.entity.OrderTable
import junseok.snr.ecommerce.order.entity.OrderItemTable
import junseok.snr.ecommerce.order.mapper.toOrderDto
import junseok.snr.ecommerce.order.repository.OrderItemRepository
import junseok.snr.ecommerce.order.repository.OrderRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderQueryServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) : OrderQueryService {
    override fun getOrderById(query: GetOrderByIdQuery): Mono<GetOrderByIdResult> {
        return orderRepository.findOrderWithItemsById(query.orderId)
            .collectList()
            .map { rows ->
                if (rows.isEmpty()) throw NoSuchElementException("Order not found with id: ${query.orderId}")
                val first = rows.first()
                val order = OrderTable(
                    orderId = first.orderId,
                    userId = first.userId,
                    orderDate = first.orderDate,
                    status = first.status,
                    totalAmount = first.totalAmount,
                    createdAt = first.createdAt,
                    updatedAt = first.updatedAt,
                )

                val orderItems = rows.filter { it.orderItemId != null }
                    .map {
                        OrderItemTable(
                            orderItemId = it.orderItemId!!,
                            orderId = order.orderId,
                            productId = it.productId!!,
                            quantity = it.quantity!!,
                            price = it.price!!
                        )
                    }

                order.orderItems = orderItems
                GetOrderByIdResult(
                    order.toOrderDto()
                )
            }
    }

    override fun getOrdersByUserId(query: GetOrdersByUserIdQuery): Mono<GetOrdersByUserIdResult> {
        return orderRepository.findByUserId(query.userId)
            .flatMap { order ->
                orderItemRepository.findByOrderId(order.orderId!!)
                    .collectList()
                    .map { orderItems ->
                        Pair(order, orderItems)
                    }
            }
            .collectList()
            .map { orderItemPairs ->
                val orderEntities = orderItemPairs.map { it.first }
                val orderItemEntities = orderItemPairs.flatMap { it.second }
                GetOrdersByUserIdResult(
                    orders = orderEntities.map {
                        it.toOrderDto(orderItemEntities)
                    }
                )
            }
    }
}