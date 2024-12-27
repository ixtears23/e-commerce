package junseok.snr.ecommerce.order

import junseok.snr.ecommerce.core.CoreConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@Import(CoreConfiguration::class)
@SpringBootApplication
class OrderApplication

fun main(args: Array<String>) {
    val springApplication = SpringApplication(OrderApplication::class.java)
    springApplication.run(*args)
}
