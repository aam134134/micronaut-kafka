package micronaut.kafka.example.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import micronaut.kafka.example.service.LunchFrequencyService

@Controller("/frequencies")
class LunchFrequencyController(private val lunchFrequencyService: LunchFrequencyService) {

    @Get
    fun listLunchFrequencies() = lunchFrequencyService.listLunchesServed()
}