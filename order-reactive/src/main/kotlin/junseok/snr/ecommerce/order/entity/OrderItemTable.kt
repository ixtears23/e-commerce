package junseok.snr.ecommerce.order.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("order_items")
data class OrderItemTable(
    @Id
    var orderItemId: Long = 0,
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal
)