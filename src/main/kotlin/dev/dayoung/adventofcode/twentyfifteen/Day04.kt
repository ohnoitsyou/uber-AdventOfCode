package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.security.MessageDigest

@Component
class Day04: PuzzleSolution(2015, 4) {
    val md = MessageDigest.getInstance("MD5")

    suspend fun runHashes(seed: String, target: String): Int {
        var idx = 0
        while(true) {
            val bytes = md.digest("$seed$idx".toByteArray())
            val hash = bytes.joinToString("") { "%02x".format(it) }
            if(hash.startsWith(target)) {
                return idx
            }
            idx++
        }
    }

    suspend fun partOne(input: String): Int {
        return runHashes(input, "00000")
    }

    suspend fun partTwo(input: String): Int {
        return runHashes(input, "000000")
    }

    override fun solve(sampleMode: Boolean) {
        println("2015 Day 04")
        val input = "yzbqklnj"
        runBlocking {
            launch {
                println("Part One: ${partOne(input)}")
            }
            launch {
                println("Part Two: ${partTwo(input)}")
            }
        }
    }

}