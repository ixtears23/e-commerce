package junseok.snr.ecommerce.order.repository

import junseok.snr.ecommerce.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, Long> {
    fun findByUserId(userId: Long): List<OrderEntity>
}