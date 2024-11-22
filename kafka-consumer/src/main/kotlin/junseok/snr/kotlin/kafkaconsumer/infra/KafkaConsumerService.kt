package junseok.snr.kotlin.kafkaconsumer.infra

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    @KafkaListener(topics = ["test-topic"], groupId = "test-group")
    fun consume(message: String) {
        println("Received message: $message")
    }
}
