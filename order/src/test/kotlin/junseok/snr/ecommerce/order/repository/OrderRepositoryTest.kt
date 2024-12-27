package junseok.snr.ecommerce.order.repository

import junseok.snr.ecommerce.order.entity.OrderEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles(value = ["dev"])
class OrderRepositoryTest @Autowired constructor(val orderRepository: OrderRepository) {

    @Test
    fun `주문 정보를 등록하는 테스트`() {
        val userId: Long = 1L

        val order = OrderEntity(
            userId = userId,
            orderDate = LocalDateTime.now()
        )

        orderRepository.save(order)

        val orderList = orderRepository.findByUserId(userId = userId)

        assertContains(orderList, order)
        assertEquals(orderList.first(), order)
        assertEquals(orderList.first().userId, userId)

    }
}