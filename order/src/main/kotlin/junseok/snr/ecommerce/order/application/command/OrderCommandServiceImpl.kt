package junseok.snr.ecommerce.order.application.command

import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand
import junseok.snr.ecommerce.core.order.application.command.CreateOrderResult
import junseok.snr.ecommerce.core.order.application.command.UpdateOrderStatusResult
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.entity.OrderEntity
import junseok.snr.ecommerce.order.entity.OrderItemEntity
import junseok.snr.ecommerce.order.mapper.toOrderDto
import junseok.snr.ecommerce.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class OrderCommandServiceImpl(
    private val orderRepository: OrderRepository
): OrderCommandService {
    @Transactional
    override fun createOrder(command: CreateOrderCommand): CreateOrderResult {
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
        return CreateOrderResult(
            orderRepository.save(order)
                .toOrderDto()
        )
    }

    @Transactional
    override fun updateOrderStatus(orderId: Long, status: OrderStatus): UpdateOrderStatusResult {
        val order = orderRepository.findById(orderId)
            .orElseThrow { NoSuchElementException("Order not found with id: $orderId") }
        order.updateStatus(status)

        return UpdateOrderStatusResult(
            order.toOrderDto()
        )
    }
}