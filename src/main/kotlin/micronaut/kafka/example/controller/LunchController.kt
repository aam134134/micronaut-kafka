package micronaut.kafka.example.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import micronaut.kafka.example.service.LunchService

@Controller("/lunches")
class LunchController(private val lunchService: LunchService) {

    @Get
    fun listAllLunches() = lunchService.getAllLunches()
}