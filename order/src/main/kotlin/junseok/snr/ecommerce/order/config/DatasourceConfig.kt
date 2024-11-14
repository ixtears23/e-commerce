package junseok.snr.ecommerce.order.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

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
        return ConnectionFactories.get(
            builder()
                .option(DRIVER, "h2")
                .option(PROTOCOL, "mem")
                .option(USER, "sa")
                .option(DATABASE, "dev_db")
            .build()
        )
    }

}