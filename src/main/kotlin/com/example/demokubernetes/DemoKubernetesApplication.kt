package com.example.demokubernetes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@SpringBootApplication
class DemoKubernetesApplication

fun main(args: Array<String>) {
    runApplication<DemoKubernetesApplication>(*args)
}

@RestController
class TestController {


    @GetMapping("/hello")
    fun testData(): Mono<Map<String,String>> = Mono.just(mapOf(Pair("test", "Hello from kubernetes")));

}
