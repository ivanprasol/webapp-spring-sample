package org.ivan.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataController {
    private Logger log = LogManager.getLogger();

    @GetMapping("test.json")
    public Mono<ResultObject> fetchResultObject() {
        log.info("Creating data object...");

        return Mono.just(new ResultObject().setId(1L).setName("data"));
    }

    private class ResultObject {
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public ResultObject setId(long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public ResultObject setName(String name) {
            this.name = name;
            return this;
        }
    }
}
