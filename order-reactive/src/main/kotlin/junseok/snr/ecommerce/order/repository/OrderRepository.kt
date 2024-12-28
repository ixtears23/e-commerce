package junseok.snr.ecommerce.order.repository

import junseok.snr.ecommerce.order.entity.OrderTable
import junseok.snr.ecommerce.order.entity.OrderItemTable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface OrderRepository : ReactiveCrudRepository<OrderTable, Long> {
    @Query("""
        SELECT order_id,
               created_at,
               order_date,
               status,
               total_amount,
               updated_at,
               user_id
          FROM orders
         WHERE user_id = :userId
        """)
    fun findByUserId(@Param("userId") userId: Long): Flux<OrderTable>

    @Query("""
        SELECT order_id,
               created_at,
               order_date,
               status,
               total_amount,
               updated_at,
               user_id
          FROM orders
         WHERE order_id = :orderId
        """)
    fun findByOrderId(@Param("orderId") orderId: Long): Mono<OrderTable>

    @Query("""
        SELECT o.*, oi.* 
        FROM orders o 
        LEFT JOIN order_items oi ON o.order_id = oi.order_id 
        WHERE o.order_id = :orderId
    """)
    fun findOrderWithItemsById(@Param("orderId") orderId: Long): Flux<OrderWithItemDto>
}

@Repository
interface OrderItemRepository : ReactiveCrudRepository<OrderItemTable, Long> {
    fun findByOrderId(orderId: Long): Flux<OrderItemTable>
}