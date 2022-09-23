package micronaut.kafka.example.kafka

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import micronaut.kafka.example.model.Lunch
import micronaut.kafka.example.service.LunchFrequencyService

@KafkaListener(groupId = "lunch-listener")
class KafkaListener(private val lunchFrequencyService: LunchFrequencyService) {

    @Topic("service")
    fun recordLunchServed(@KafkaKey lunchId: String, lunch: Lunch) {
        lunchFrequencyService.recordLunchServed(lunch)
    }
}