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
                val order = rows[0]["order_id"]?.let { orderId ->
                    OrderTable(
                        orderId = orderId as Long,
                        userId = rows[0]["user_id"] as Long,
                        orderDate = rows[0]["order_date"] as LocalDateTime,
                        status = rows[0]["status"] as String,
                        totalAmount = rows[0]["total_amount"] as BigDecimal,
                        createdAt = rows[0]["created_at"] as LocalDateTime,
                        updatedAt = rows[0]["updated_at"] as LocalDateTime?,
//                        orderItems = mutableListOf()
                    )
                } ?: throw NoSuchElementException("Order not found with id: ${query.orderId}")

                val orderItems = rows.filter { it["order_item_id"] != null }
                    .map { row ->
                        OrderItemTable(
                            orderItemId = row["order_item_id"] as Long,
                            orderId = order.orderId,
                            productId = row["product_id"] as Long,
                            quantity = row["quantity"] as Int,
                            price = row["price"] as BigDecimal
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
                        it.toOrderDto()
                    }
                )
            }
    }
}