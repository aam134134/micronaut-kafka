package micronaut.kafka.example.listener

import jakarta.inject.Inject
import micronaut.kafka.example.model.Lunch
import micronaut.kafka.example.service.LunchFrequencyService
import micronaut.kafka.example.service.LunchService
import micronaut.kafka.example.MicronautKafkaTester
import org.awaitility.Awaitility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class KafkaTest : MicronautKafkaTester() {

    @Inject
    lateinit var lunchFrequencyService: LunchFrequencyService

    @Inject
    lateinit var lunchService: LunchService

    @Test
    fun testFrequency() {
        val sandwich = Lunch("Sandwich", "l1")
        val pizza = Lunch("Pizza", "l2")

        lunchService.serveLunch(sandwich.lunchId)
        lunchService.serveLunch(sandwich.lunchId)
        lunchService.serveLunch(sandwich.lunchId)
        lunchService.serveLunch(pizza.lunchId)

        Awaitility.await().atMost(10, TimeUnit.SECONDS).until {
            lunchFrequencyService.getLunchFrequency(pizza) > 0
        }

        Assertions.assertEquals(3, lunchFrequencyService.getLunchFrequency(sandwich))
        Assertions.assertEquals(1, lunchFrequencyService.getLunchFrequency(pizza))
    }
}