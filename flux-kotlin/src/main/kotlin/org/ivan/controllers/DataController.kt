package org.ivan.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["data"], produces = [MediaType.APPLICATION_JSON_VALUE])
open class DataController {
    private val log = LogManager.getLogger()

    @GetMapping("test.json")
    open fun fetchResultObject() : Mono<ResultObject> {
        log.info("Creating data object...")

        return Mono.just(ResultObject(id = 1L, name = "data"))
    }

    class ResultObject(val id: Long, val name: String)
}