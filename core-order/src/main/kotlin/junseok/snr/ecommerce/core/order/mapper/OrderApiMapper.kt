package junseok.snr.ecommerce.core.order.mapper

import junseok.snr.ecommerce.core.order.api.dto.OrderApiDto
import junseok.snr.ecommerce.core.order.api.dto.OrderItemApiDto
import junseok.snr.ecommerce.core.order.application.dto.OrderDto
import junseok.snr.ecommerce.core.order.application.dto.OrderItemDto

fun OrderItemDto.toOrderItemApiDto(): OrderItemApiDto {
    return OrderItemApiDto(
        orderItemId = orderItemId,
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
    )
}

fun OrderDto.toOrderApiDto(): OrderApiDto {
    return OrderApiDto(
        orderId = orderId,
        userId = userId,
        orderDate = orderDate,
        status = status,
        totalAmount = totalAmount,
        orderItems = orderItems.map {
            it.toOrderItemApiDto()
        },
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}