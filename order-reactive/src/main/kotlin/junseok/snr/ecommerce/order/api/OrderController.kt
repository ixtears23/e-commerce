package junseok.snr.ecommerce.order.api

import junseok.snr.ecommerce.core.order.api.request.CreateOrderRequest
import junseok.snr.ecommerce.core.order.api.response.CreateOrderResponse
import junseok.snr.ecommerce.core.order.api.response.UpdateOrderStatusResponse
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrderByIdResult
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdQuery
import junseok.snr.ecommerce.core.order.application.query.GetOrdersByUserIdResult
import junseok.snr.ecommerce.core.order.mapper.toCreateOrderCommand
import junseok.snr.ecommerce.core.order.mapper.toOrderApiDto
import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import junseok.snr.ecommerce.order.application.command.OrderCommandService
import junseok.snr.ecommerce.order.application.query.OrderQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderCommandService: OrderCommandService,
    private val orderQueryService: OrderQueryService,
) {
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): Mono<ResponseEntity<CreateOrderResponse>> {
        return orderCommandService.createOrder(
            request.toCreateOrderCommand()
        ).map { createOrderResult ->
            ResponseEntity.created(URI.create("/api/v1/orders/${createOrderResult.order.orderId}"))
                .body(
                    CreateOrderResponse(
                        createOrderResult.order.toOrderApiDto()
                    )
                )
        }
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long): Mono<ResponseEntity<GetOrderByIdResult>> {
        return orderQueryService.getOrderById(
            GetOrderByIdQuery(orderId)
        ).map { getOrderResult ->
            ResponseEntity.ok(
                getOrderResult
            )
        }
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): Mono<ResponseEntity<GetOrdersByUserIdResult>> {
        return orderQueryService.getOrdersByUserId(GetOrdersByUserIdQuery(userId))
            .map { getOrdersByUserIdResult ->
                ResponseEntity.ok(
                    getOrdersByUserIdResult
                )
            }
    }

    @PutMapping("/{orderId}/status")
    fun updateOrderStatus(
        @PathVariable orderId: Long,
        @RequestParam status: OrderStatus
    ): Mono<ResponseEntity<UpdateOrderStatusResponse>> {
        return orderCommandService.updateOrderStatus(orderId, status)
            .map { updateOrderStatusResult ->
                ResponseEntity.ok(
                    UpdateOrderStatusResponse(
                        updateOrderStatusResult.order.toOrderApiDto()
                    )
                )
            }
    }
}