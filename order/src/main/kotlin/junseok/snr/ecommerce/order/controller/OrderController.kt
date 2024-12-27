package junseok.snr.ecommerce.order.controller

import junseok.snr.ecommerce.core.order.api.request.CreateOrderRequest
import junseok.snr.ecommerce.core.order.api.response.OrderItemResponseDto
import junseok.snr.ecommerce.core.order.api.response.OrderResponse
import junseok.snr.ecommerce.core.order.application.command.CreateOrderCommand
import junseok.snr.ecommerce.core.order.application.command.OrderItem
import junseok.snr.ecommerce.core.order.application.query.GetOrderQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
        private val orderService: OrderService
) {
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<OrderResponse> {
        val command = CreateOrderCommand(
                userId = request.userId,
                orderItems = request.orderItems.map {
                    OrderItem(
                            productId = it.productId,
                            quantity = it.quantity,
                            price = it.price
                    )
                }
        )
        val createdOrder = orderService.createOrder(command)
        return ResponseEntity.created(URI.create("/api/v1/orders/${createdOrder.orderId}"))
                .body(
                        OrderResponse(
                                orderId = createdOrder.orderId,
                                userId = createdOrder.userId,
                                orderDate = createdOrder.orderDate,
                                status = createdOrder.status,
                                totalAmount = createdOrder.totalAmount,
                                orderItems = createdOrder.orderItems.map {
                                    OrderItemResponseDto(
                                            productId = it.productId,
                                            quantity = it.quantity,
                                            price = it.price
                                    )
                                },
                                createdAt = createdOrder.createdAt,
                                updatedAt = createdOrder.updatedAt
                        )
                )
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<OrderResponse> {
        val order = orderService.getOrderById(GetOrderQuery(orderId))
        return ResponseEntity.ok(
                OrderResponse(
                        orderId = order.orderId,
                        userId = order.userId,
                        orderDate = order.orderDate,
                        status = order.status,
                        totalAmount = order.totalAmount,
                        orderItems = order.orderItems.map {
                            OrderItemResponseDto(
                                    productId = it.productId,
                                    quantity = it.quantity,
                                    price = it.price
                            )
                        },
                        createdAt = order.createdAt,
                        updatedAt = order.updatedAt
                )
        )
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): ResponseEntity<List<OrderResponse>> {
        val orders = orderService.getOrdersByUserId(GetOrdersByUserIdQuery(userId))
        return ResponseEntity.ok(
                orders.map {
                    OrderResponse(
                            orderId = it.orderId,
                            userId = it.userId,
                            orderDate = it.orderDate,
                            status = it.status,
                            totalAmount = it.totalAmount,
                            orderItems = it.orderItems.map {
                                OrderItemResponseDto(
                                        productId = it.productId,
                                        quantity = it.quantity,
                                        price = it.price
                                )
                            },
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt
                    )
                }
        )
    }

    @PutMapping("/{orderId}/status")
    fun updateOrderStatus(
            @PathVariable orderId: Long,
            @RequestParam status: OrderStatus
    ): ResponseEntity<OrderResponse> {
        val updatedOrder = orderService.updateOrderStatus(orderId, status)
        return ResponseEntity.ok(
                OrderResponse(
                        orderId = updatedOrder.orderId,
                        userId = updatedOrder.userId,
                        orderDate = updatedOrder.orderDate,
                        status = updatedOrder.status,
                        totalAmount = updatedOrder.totalAmount,
                        orderItems = updatedOrder.orderItems.map {
                            OrderItemResponseDto(
                                    productId = it.productId,
                                    quantity = it.quantity,
                                    price = it.price
                            )
                        },
                        createdAt = updatedOrder.createdAt,
                        updatedAt = updatedOrder.updatedAt
                )
        )
    }
}