package junseok.snr.ecommerce.order.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
class DatasourceConfig {

    @Profile("dev")
    @Bean
    fun mysqlDataSource(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String
    ): DataSource {
        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = url
            this.driverClassName = "com.mysql.cj.jdbc.Driver"
            this.username = username
            this.password = password
            this.maximumPoolSize = 30
            this.minimumIdle = 10
            this.connectionTimeout = 30000
            this.idleTimeout = 30000
            this.maxLifetime = 1800000
            this.leakDetectionThreshold = 2000
            this.connectionTestQuery = "SELECT 1"
        }

        return HikariDataSource(hikariConfig)
    }

    @Profile("test")
    @Bean
    fun h2DataSource(@Value("\${spring.datasource.url}") url: String,
                     @Value("\${spring.datasource.username}") username: String,
                     @Value("\${spring.datasource.password}") password: String,): DataSource {
        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = url
            this.driverClassName = "org.h2.Driver"
            this.username = username
            this.password = password
            this.maximumPoolSize = 30
            this.minimumIdle = 10
            this.connectionTimeout = 30000
            this.idleTimeout = 30000
            this.maxLifetime = 1800000
            this.leakDetectionThreshold = 2000
            this.connectionTestQuery = "SELECT 1"
        }

        return HikariDataSource(hikariConfig)
    }
}