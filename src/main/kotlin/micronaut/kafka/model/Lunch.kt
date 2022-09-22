package micronaut.kafka.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class Lunch(val mainCourse: String, val lunchId: String)