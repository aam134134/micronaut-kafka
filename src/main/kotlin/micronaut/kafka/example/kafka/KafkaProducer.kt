package micronaut.kafka.example.kafka

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import micronaut.kafka.example.model.Lunch

@KafkaClient(id = "lunch-client", acks = KafkaClient.Acknowledge.ALL)
interface KafkaProducer {

    @Topic("service")
    fun lunchServed(@KafkaKey lunchId: String, lunch: Lunch)
}