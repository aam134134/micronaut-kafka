package micronaut.kafka.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class LunchCount(val lunchId: String, val count: Long)