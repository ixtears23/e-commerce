package junseok.snr.ecommerce.core.order.model.enums

enum class OrderStatus(val description: String) {
    PENDING("결제 대기 중"),
    PAID("결제 완료"),
    SHIPPED("배송 중"),
    COMPLETED("거래 완료"),
    CANCELLED("주문 취소")
}