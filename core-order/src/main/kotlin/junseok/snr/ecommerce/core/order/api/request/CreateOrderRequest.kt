package junseok.snr.ecommerce.core.order.api.request

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

// 주문 생성 요청 DTO
data class CreateOrderRequest(
        val userId: Long,
        val orderItems: List<OrderItemDto>
)

// 상품 정보를 담는 DTO
data class OrderItemDto(
        val productId: Long,
        val quantity: Int,
        val price: BigDecimal
)


