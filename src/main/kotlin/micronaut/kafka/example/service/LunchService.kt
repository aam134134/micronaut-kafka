package micronaut.kafka.example.service

import jakarta.inject.Singleton
import micronaut.kafka.example.kafka.KafkaProducer
import micronaut.kafka.example.model.Lunch
import javax.annotation.PostConstruct

@Singleton
class LunchService(private val lunchFrequencyKafkaClient: KafkaProducer) {

    private val cafeteria = mutableListOf<Lunch>()

    @PostConstruct
    fun init() {
        cafeteria.apply {
            this.add(Lunch("Sandwich", "l1"))
            this.add(Lunch("Pizza", "l2"))
        }
    }

    fun getAllLunches() = cafeteria

    fun serveLunch(lunchId: String) {
        val lunch = cafeteria.stream().filter { it.lunchId == lunchId }.findFirst().orElseThrow()
        lunch.run {
            lunchFrequencyKafkaClient.lunchServed(this.lunchId, this)
        }
    }

}