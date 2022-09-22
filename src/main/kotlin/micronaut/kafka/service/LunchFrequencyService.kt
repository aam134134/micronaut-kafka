package micronaut.kafka.service

import jakarta.inject.Singleton
import micronaut.kafka.model.Lunch
import micronaut.kafka.model.LunchCount
import java.util.concurrent.ConcurrentHashMap

@Singleton
class LunchFrequencyService {
    private val lunchesServed: MutableMap<Lunch, Long> = ConcurrentHashMap()

    fun recordLunchServed(lunch: Lunch) {
        lunchesServed.compute(lunch) { _, count ->
            if (count == null)
                return@compute 1L
            else
                return@compute count + 1
        }
    }

    fun listLunchesServed(): List<LunchCount> = lunchesServed.entries.map { (k, v) -> LunchCount(k.lunchId, v) }

    fun getLunchFrequency(lunch: Lunch): Long = lunchesServed.getOrDefault(lunch, 0)
}