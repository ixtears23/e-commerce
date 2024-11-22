package junseok.snr.kotlin.kafkaproducer.web

import junseok.snr.kotlin.kafkaproducer.infra.KafkaProducerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val kafkaProducerService: KafkaProducerService) {
    @GetMapping("/send")
    fun sendMessage(): String {
        kafkaProducerService.sendMessage("test-topic", "Hello, Kafka!")
        return "Message sent!"
    }
}