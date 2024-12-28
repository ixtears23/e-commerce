package junseok.snr.ecommerce.order.application.command

import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand
import junseok.snr.ecommerce.core.order.application.command.CreateOrderResult
import junseok.snr.ecommerce.core.order.application.command.UpdateOrderStatusResult
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.entity.OrderItemTable
import junseok.snr.ecommerce.order.entity.OrderTable
import junseok.snr.ecommerce.order.mapper.toOrderDto
import junseok.snr.ecommerce.order.repository.OrderItemRepository
import junseok.snr.ecommerce.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderCommandServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
): OrderCommandService {
    @Transactional
    override fun createOrder(command: CreateOrderCommand): Mono<CreateOrderResult> {
        val order = OrderTable(
            userId = command.userId,
            orderDate = LocalDateTime.now(),
            status = OrderStatus.PENDING.name,
            totalAmount = command.orderItems.sumOf { it.price * BigDecimal(it.quantity) },
        )

        return orderRepository.save(order)
            .flatMap { savedOrder ->
                val orderItems = command.orderItems.map { item ->
                    OrderItemTable(
                        orderId = savedOrder.orderId,
                        productId = item.productId,
                        quantity = item.quantity,
                        price = item.price
                    )
                }

                orderItemRepository.saveAll(orderItems)
                    .collectList()
                    .map {
                        CreateOrderResult(
                            savedOrder.toOrderDto()
                        )
                    }
            }
    }

    @Transactional
    override fun updateOrderStatus(orderId: Long, status: OrderStatus): Mono<UpdateOrderStatusResult> {
        return orderRepository.findByOrderId(orderId)
            .switchIfEmpty(Mono.error(NoSuchElementException("Order not found with id: $orderId")))
            .flatMap { order ->
                order.updateStatus(status)
                orderRepository.save(order)
            }

            .flatMap { savedOrder ->
                orderItemRepository.findByOrderId(orderId)
                    .collectList()
                    .map { orderItems ->
                        GetOrderByIdResult(
                            savedOrder.toOrderDto()
                        )
                    }
            }.map {
                UpdateOrderStatusResult(it.order)
            }
    }
}