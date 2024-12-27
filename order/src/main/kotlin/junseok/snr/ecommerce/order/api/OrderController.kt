package junseok.snr.ecommerce.order.api

import junseok.snr.ecommerce.core.order.api.request.CreateOrderRequest
import junseok.snr.ecommerce.core.order.api.response.CreateOrderResponse
import junseok.snr.ecommerce.core.order.api.response.UpdateOrderStatusResponse
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.application.command.OrderCommandService
import junseok.snr.ecommerce.order.application.query.OrderQueryService
import junseok.snr.ecommerce.order.mapper.toCreateOrderCommand
import junseok.snr.ecommerce.order.mapper.toOrderApiDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
        private val orderCommandService: OrderCommandService,
        private val orderQueryService: OrderQueryService,
) {
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderResponse> {
        val createdOrder = orderCommandService.createOrder(
            request.toCreateOrderCommand()
        )

        return ResponseEntity.created(URI.create("/api/v1/orders/${createdOrder.order.orderId}"))
                .body(
                    CreateOrderResponse(
                        createdOrder.order.toOrderApiDto()
                    )
                )
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<GetOrderByIdResult> {
        return ResponseEntity.ok(
            orderQueryService.getOrderById(
                GetOrderByIdQuery(orderId)
            )
        )
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): ResponseEntity<GetOrdersByUserIdResult> {
        return ResponseEntity.ok(
            orderQueryService.getOrdersByUserId(GetOrdersByUserIdQuery(userId))
        )
    }

    @PutMapping("/{orderId}/status")
    fun updateOrderStatus(
            @PathVariable orderId: Long,
            @RequestParam status: OrderStatus
    ): ResponseEntity<UpdateOrderStatusResponse> {
        return ResponseEntity.ok(
            UpdateOrderStatusResponse(
                orderCommandService.updateOrderStatus(orderId, status)
                    .order.toOrderApiDto()
            )
        )
    }
}