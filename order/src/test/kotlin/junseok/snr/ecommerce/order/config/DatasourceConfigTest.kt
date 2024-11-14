package junseok.snr.ecommerce.order.config

import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class DatasourceConfigTest {

    @Autowired
    lateinit var connectionFactory: ConnectionFactory


    @Test
    fun `should load development connectionFactory bean`() {
        assertNotNull(connectionFactory)
    }

}
