package junseok.snr.ecommerce.order.mapper

import junseok.snr.ecommerce.core.order.application.dto.OrderDto
import junseok.snr.ecommerce.core.order.application.dto.OrderItemDto
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.entity.OrderItemTable
import junseok.snr.ecommerce.order.entity.OrderTable

fun OrderItemTable.toOrderItemDto(): OrderItemDto {
    return OrderItemDto(
        orderItemId = orderItemId,
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
    )
}

fun OrderTable.toOrderDto(): OrderDto {
    return OrderDto(
        orderId = orderId,
        userId = userId,
        orderDate = orderDate,
        status = OrderStatus.valueOf(status),
        totalAmount = totalAmount,
        orderItems = orderItems.map {
            it.toOrderItemDto()
        },
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}