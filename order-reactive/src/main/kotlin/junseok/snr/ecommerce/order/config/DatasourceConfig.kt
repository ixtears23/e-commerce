package junseok.snr.ecommerce.order.config

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.time.Duration

@Configuration
@EnableR2dbcRepositories
class DatasourceConfig : AbstractR2dbcConfiguration() {
    @Value("\${spring.r2dbc.database}")
    private lateinit var database: String
    @Value("\${spring.r2dbc.host}")
    private lateinit var host: String
    @Value("\${spring.r2dbc.port}")
    private lateinit var port: String
    @Value("\${spring.r2dbc.username}")
    private lateinit var username: String
    @Value("\${spring.r2dbc.password}")
    private lateinit var password: String

    @Primary
    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val configuration = com.github.jasync.sql.db.Configuration(
            username = username,
            password = password,
            database = database,
            host = host,
            port = port.toInt()
        )
        return JasyncConnectionFactory(MySQLConnectionFactory(configuration))
    }

    @Profile("test")
    @Bean
    fun testConnectionFactory(connectionFactory: ConnectionFactory): ConnectionFactory {
        val poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxSize(120)
            .initialSize(28)
            .maxIdleTime(Duration.ofSeconds(10))
            .maxLifeTime(Duration.ofSeconds(10))
            .maxAcquireTime(Duration.ofSeconds(10))
            .maxValidationTime(Duration.ofSeconds(1))
            .name("Test MySQL Connection Pool")
            .build()


        return ConnectionPool(poolConfiguration)
    }

}