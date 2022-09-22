package micronaut.kafka.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import micronaut.kafka.model.Lunch
import  org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class LunchFrequencyServiceTest {

    @Inject
    lateinit var lunchFrequencyService: LunchFrequencyService

    @Test
    fun testUpdateAndGetLunchFrequency() {
        val ham = Lunch("ham sandwich", "l1")
        val pizza = Lunch("Pizza", "l2")

        lunchFrequencyService.recordLunchServed(ham)
        lunchFrequencyService.recordLunchServed(ham)
        lunchFrequencyService.recordLunchServed(pizza)

        Assertions.assertEquals(2, lunchFrequencyService.getLunchFrequency(ham))
        Assertions.assertEquals(1, lunchFrequencyService.getLunchFrequency(pizza))
    }
}