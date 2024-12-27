package junseok.snr.ecommerce.core.order.api.response

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

// 주문 조회 응답 DTO
data class OrderResponse(
        val orderId: Long,
        val userId: Long,
        val orderDate: LocalDateTime,
        val status: OrderStatus,
        val totalAmount: BigDecimal,
        val orderItems: List<OrderItemResponseDto>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?
)

// 주문 상품 정보를 담는 DTO
data class OrderItemResponseDto(
        val productId: Long,
        val quantity: Int,
        val price: BigDecimal
)