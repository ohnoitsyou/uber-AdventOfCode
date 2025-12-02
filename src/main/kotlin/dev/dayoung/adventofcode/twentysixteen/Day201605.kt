package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class Day201605 : PuzzleSolution(2016, 5, true) {
    val md: MessageDigest = MessageDigest.getInstance("MD5")

    @OptIn(ExperimentalStdlibApi::class)
    private fun doSolve(input: String): Pair<String, String> {
        val partOne = CharArray(8)
        val partTwo = CharArray(8)
        var idx = 0
        var i = 0
        var hash: String
        while(partOne.any { it.code == 0 } || partTwo.any { it.code == 0}) {
            do {
                hash = md.digest("$input$idx".toByteArray()).toHexString()
                idx++
            } while(!hash.startsWith("00000"))
            if(i <= partOne.lastIndex) {
                partOne[i] = hash[5]
                i++
            }
            if(hash[5].code >= '0'.code && hash[5].code <= '9'.code) {
                val loc: Int = Integer.parseInt(hash[5].toString())
                if (loc <= partTwo.lastIndex && partTwo[loc].code == 0) {
                    partTwo[loc] = hash[6]
                }
            }
        }
        return Pair(partOne.concatToString(), partTwo.concatToString())
    }

    override fun solve(sampleMode: Boolean) {
        val input = Utils.readInputResource("2016/five.txt", sampleMode)?.first()!!
        val (pOne, pTwo) = doSolve(input)
        println("Part one: $pOne\nPart two: $pTwo")
    }
}
