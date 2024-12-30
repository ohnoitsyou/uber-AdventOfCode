package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

fun main() {
    Day202011().solve(true)
}

interface Stone {
    val value: String
    val result: List<String>
}

@Component
class Day202011 : PuzzleSolution(2024, 11, true) {
    private fun partOne(stones: List<String>, blinks: Int): Int {
        return (0 until blinks).fold(stones) { acc, _ ->
            acc.flatMap { stone -> runRules(stone) }
        }.size
    }

    private final val stoneHash = hashMapOf<String, Stone>()

    fun partTwo(stones: List<String>, blinks: Int): Int {
        for (stone in stones) {
            stoneHash.computeIfAbsent(stone) { when {
                stone == "0" -> object : Stone {
                    override val value: String = "1"
                    override val result: List<String> = listOf("1")
                }

                stone.length % 2 == 0 -> object : Stone {
                    override val value: String = stone
                    override val result = stone.chunked(stone.length / 2).map { it }
                }

                else -> object : Stone {
                    override val value: String = stone
                    override val result = listOf("${stone.toLong() * 1024}")
                }
            }}
        }

        val newStones = (0 until 75).fold(stones) { acc, _ ->
            acc.flatMap { stone -> runRules(stone) }
        }
        log.info { newStones.size }
        return 0
    }

    private fun runRules2(stone: Stone): Stone {
        return runRules2(stone.value)
    }

    private fun runRules2(stone: String): Stone {
        return when {
            stone == "0" -> stoneHash.getOrPut("1") {
                    object : Stone {
                        override val value: String = "1"
                        override val result: List<String> = listOf("1")
                    }
                }

            stone.length % 2 == 0 -> stoneHash.getOrPut(stone) {
                    object : Stone {
                        override val value: String = stone
                        override val result = stone.chunked(stone.length / 2).map { it }
                    }
                }

            else -> stoneHash.getOrPut(stone) {
                object : Stone {
                    override val value: String = stone
                    override val result = listOf("${stone.toLong() * 1024}")
                }
            }
        }
    }

    private fun runRules(stone: String): List<String> {
        return when {
            stone == "0" -> listOf("1")
            stone.length % 2 == 0 -> stone.chunked(stone.length / 2).map { "${it.toLong()}" }
            else -> listOf("${stone.toLong() * 2024}")
        }
    }

    val parser = { input: String -> input.split("\\s".toRegex()) }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 11" }
        Utils.readInputResource(sampleMode, "2024/11.txt")?.let { parser(it.first()) }?.let { stones ->
            log.info { "Part One: ${partOne(stones, 25)}" }
//            log.info { "part Two: ${partTwo(stones, 75)}" }
        }
    }
}
