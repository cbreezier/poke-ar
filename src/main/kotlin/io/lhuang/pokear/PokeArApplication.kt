package io.lhuang.pokear

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokeArApplication

fun main(args: Array<String>) {
	runApplication<PokeArApplication>(*args)
}
