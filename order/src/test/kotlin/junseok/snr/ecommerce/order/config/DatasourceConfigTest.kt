package junseok.snr.ecommerce.order.config

import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class DatasourceConfigTest {

    @Autowired
    lateinit var hikariDataSource: HikariDataSource

    @Test
    fun `should load development connectionFactory bean`() {
        assertNotNull(hikariDataSource)
    }

}
