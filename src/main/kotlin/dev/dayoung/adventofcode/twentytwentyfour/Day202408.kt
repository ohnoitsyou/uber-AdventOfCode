package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class Day202408: PuzzleSolution(2024, 8, true) {
    private val log = KotlinLogging.logger {}

    private fun partOne() { }

    private val parser = { input: List<String> -> input }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 08" }
        val lines = Utils.readInputResource(sampleMode, "2024/eight.txt")?.let { parser(it) }
    }
}
