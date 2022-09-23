package micronaut.kafka.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import micronaut.kafka.service.LunchFrequencyService

@Controller("/frequencies")
class LunchFrequencyController(private val lunchFrequencyService: LunchFrequencyService) {

    @Get
    fun listLunchFrequencies() = lunchFrequencyService.listLunchesServed()
}