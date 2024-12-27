package junseok.snr.ecommerce.order.mapper

import junseok.snr.ecommerce.core.order.application.dto.OrderDto
import junseok.snr.ecommerce.core.order.application.dto.OrderItemDto
import junseok.snr.ecommerce.order.entity.OrderEntity
import junseok.snr.ecommerce.order.entity.OrderItemEntity

fun OrderItemEntity.toOrderItemDto(): OrderItemDto {
    return OrderItemDto(
        orderItemId = orderItemId,
        orderId = order.orderId,
        productId = productId,
        quantity = quantity,
        price = price,
    )
}

fun OrderEntity.toOrderDto(): OrderDto {
    return OrderDto(
        orderId = orderId,
        userId = userId,
        orderDate = orderDate,
        status = status,
        totalAmount = totalAmount,
        orderItems = orderItems.map {
            it.toOrderItemDto()
        },
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}