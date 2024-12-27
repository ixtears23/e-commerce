package junseok.snr.ecommerce.order.service

import junseok.snr.ecommerce.core.order.api.enums.OrderStatus
import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand
import junseok.snr.ecommerce.core.order.application.query.GetOrderQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.order.entity.OrderEntity
import junseok.snr.ecommerce.order.entity.OrderItemEntity
import junseok.snr.ecommerce.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository
) {
    @Transactional
    fun createOrder(command: CreateOrderCommand): OrderEntity {
        val order = OrderEntity(
            userId = command.userId,
            orderDate = LocalDateTime.now(),
            status = OrderStatus.PENDING,
            totalAmount = command.orderItems.sumOf { it.price * BigDecimal(it.quantity) }
        )

        val orderItems = command.orderItems.map {
            OrderItemEntity(
                order = order,
                productId = it.productId,
                quantity = it.quantity,
                price = it.price
            )
        }.toMutableList()

        order.orderItems = orderItems
        return orderRepository.save(order)
    }

    fun getOrderById(query: GetOrderQuery): OrderEntity {
        return orderRepository.findById(query.orderId)
            .orElseThrow { NoSuchElementException("Order not found with id: ${query.orderId}") }
    }

    fun getOrdersByUserId(query: GetOrdersByUserIdQuery): List<OrderEntity> {
        return orderRepository.findByUserId(query.userId)
    }

    @Transactional
    fun updateOrderStatus(orderId: Long, status: OrderStatus): OrderEntity {
        val order = orderRepository.findById(orderId)
            .orElseThrow { NoSuchElementException("Order not found with id: $orderId") }
        order.updateStatus(status)
        return order
    }
}