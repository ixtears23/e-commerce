package junseok.snr.ecommerce.core.order.application.query
// 사용자 ID로 주문 목록 조회 Query
data class GetOrdersByUserIdQuery(
    val userId: Long
)