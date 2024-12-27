package junseok.snr.ecommerce.order.application.command

import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand
import junseok.snr.ecommerce.core.order.application.command.CreateOrderResult
import junseok.snr.ecommerce.core.order.application.command.UpdateOrderStatusResult
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import reactor.core.publisher.Mono

interface OrderCommandService {
    fun createOrder(command: CreateOrderCommand): Mono<CreateOrderResult>
    fun updateOrderStatus(orderId: Long, status: OrderStatus): Mono<UpdateOrderStatusResult>
}
