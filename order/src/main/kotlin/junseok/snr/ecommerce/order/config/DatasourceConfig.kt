package junseok.snr.ecommerce.order.config

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Duration

@Configuration
class DatasourceConfig {

    @Profile("dev")
    @Bean
    fun devConnectionFactory(): ConnectionFactory {
        return ConnectionFactories.get(
            builder()
                .option(DRIVER, "org.h2.Driver")
                .option(HOST, "localhost")
                .option(PORT, 3306)
                .option(USER, "dev_user")
                .option(PASSWORD, "dev_password")
                .option(DATABASE, "dev_db")
                .build()
        )
    }

    @Profile("test")
    @Bean
    fun testConnectionFactory(): ConnectionFactory {
        val factoryOptions = builder()
            .option(DRIVER, "h2")
            .option(PROTOCOL, "mem")
            .option(USER, "sa")
            .option(DATABASE, "dev_db")
            .build()

        val connectionFactory = ConnectionFactories.get(factoryOptions)

        val poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxSize(120)
            .initialSize(28)
            .maxIdleTime(Duration.ofSeconds(10))
            .maxLifeTime(Duration.ofSeconds(10))
            .maxAcquireTime(Duration.ofSeconds(10))
            .maxValidationTime(Duration.ofSeconds(1))
            .name("Test H2 Connection Pool")
            .build()


        return ConnectionPool(poolConfiguration)
    }

}