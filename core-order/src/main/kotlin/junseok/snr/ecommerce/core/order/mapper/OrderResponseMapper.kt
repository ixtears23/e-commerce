package junseok.snr.ecommerce.core.order.mapper

import junseok.snr.ecommerce.core.order.api.request.CreateOrderRequest
import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand


fun CreateOrderRequest.toCreateOrderCommand(): CreateOrderCommand {
    return CreateOrderCommand(
        userId = userId,
        orderItems = orderItems.map {
            it.toCreateOrderItemCommand()
        }
    )
}

fun CreateOrderRequest.CreateOrderItemRequest.toCreateOrderItemCommand(): CreateOrderCommand.CreateOrderItemCommand {
    return CreateOrderCommand.CreateOrderItemCommand(
        productId = productId,
        quantity = quantity,
        price = price,
    )
}
