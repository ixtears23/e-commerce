package junseok.snr.ecommerce.core.order.application.command

import java.math.BigDecimal

// 주문 생성 Command
data class CreateOrderCommand(
    val userId: Long,
    val orderItems: List<CreateOrderItemCommand>
) {
    // 주문 상품
    data class CreateOrderItemCommand(
        val productId: Long,
        val quantity: Int,
        val price: BigDecimal
    )
}

