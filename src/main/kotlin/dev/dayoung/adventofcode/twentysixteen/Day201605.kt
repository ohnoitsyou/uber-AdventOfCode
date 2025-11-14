package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import java.security.MessageDigest
import kotlin.collections.all

class Day201605 : PuzzleSolution(2016, 5) {
    val md = MessageDigest.getInstance("MD5")


    @OptIn(ExperimentalStdlibApi::class)
    private fun doSolve(input: String): String {
        val partOne = CharArray(8)
        val partTwo = CharArray(8)
        var idx = 0
        var i = 0
        while(partOne.any { it.code == 0 } && partTwo.any { it.code == 0}) {
//        for (i in 0 until 8) {
            var hash: ByteArray
            do {
                hash = md.digest("$input$idx".toByteArray())
                idx++
                if(idx % 10000000 == 0) {
                    println("$idx")
                }
            } while(!hash.toHexString().startsWith("00000"))
            partOne[i] = hash.toHexString()[5]
            i++
        }
        return partOne.concatToString()
    }

    override fun solve(sampleMode: Boolean) {
        val input = Utils.readInputResource(sampleMode, "2016/five.txt")?.first()!!
        println(doSolve(input))
    }
}

fun main() {
    Day201605().solve(false)
}
