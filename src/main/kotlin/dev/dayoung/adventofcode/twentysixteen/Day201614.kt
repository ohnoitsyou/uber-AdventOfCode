package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class Day201614 : PuzzleSolution(2016, 14){
    val md: MessageDigest = MessageDigest.getInstance("MD5")

    val tripRx = Regex("""(.)\1\1""")
    val fiveRx = Regex("""(.)\1\1\1\1""")

    var validCount = 0
    var idx = 0

    val hashes = mutableListOf<Pair<String, Int>>()
    val hashCache = mutableMapOf<Int, String>()

    fun partOne(input: String) {
        while(validCount < 64) {
            val candidate = md.digest("$input$idx".toByteArray()).toHexString()

            fiveRx.findAll(candidate).forEach { mr ->
                val (c) = mr.destructured
                log.info { "$idx: Found sequence of 5 $c : ${mr.value}" }
                hashes.firstOrNull {  it.first == c && idx <= it.second }?.let {
                    log.info { "found matching 5 stack: $c ${it.first}" }
                    hashes.remove(it)
                    validCount++
                }
            }

            tripRx.find(candidate)?.let { mr ->
                val (c) = mr.destructured
                hashes.add(Pair(c, idx + 1000)).also {
                    log.info { "Added new candidate: $c at $idx : ${mr.value}" }
                }
            }

            idx++
            if(hashes.isNotEmpty() && hashes.first().second < idx) hashes.removeAt(0).also { log.info { "removed candidate: $it" }}
        }
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2016/fourteen.txt", sampleMode)?.let {
            partOne(it.first())
        }
    }
}

fun main() {
    Day201614().solve(true)
}
