package junseok.snr.ecommerce.order.entity

import junseok.snr.ecommerce.core.order.model.enums.OrderStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OrderEntityTest {

    @Test
    fun `OrderEntity 가 제대로 생성 되는지 테스트 한다`() {

        val userId = 1L

        val orderEntity = OrderEntity(
            orderDate = LocalDateTime.now(),
            userId = userId
        )

        assertEquals(OrderStatus.PENDING, orderEntity.status)
    }
}