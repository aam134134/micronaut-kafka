package micronaut.kafka.listener

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import micronaut.kafka.model.Lunch
import micronaut.kafka.service.LunchFrequencyService

@KafkaListener(groupId = "lunch-listener")
class KafkaListener(private val lunchFrequencyService: LunchFrequencyService) {

    @Topic("service")
    fun recordLunchServed(@KafkaKey lunchId: String, lunch: Lunch) {
        lunchFrequencyService.recordLunchServed(lunch)
    }
}