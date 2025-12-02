package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day201610 : PuzzleSolution(2016, 10) {

    val setupRx  = Regex("""value (\d+) goes to bot (\d+)""")
    val botBotRx = Regex("""bot (\d+) gives low to bot (\d+) and high to bot (\d+)""")
    val outBotRx = Regex("""bot (\d+) gives low to output (\d+) and high to bot (\d+)""")
    val botOutRx = Regex("""bot (\d+) gives low to bot (\d+) and high to output (\d+)""")
    val outOutRx = Regex("""bot (\d+) gives low to output (\d+) and high to output (\d+)""")

    data class Bot(val botNum: Int) {
        var highTarget: Int = -1
        var highIsOutput: Boolean = false
        var lowTarget: Int = -1
        var lowIsOutput: Boolean = false
        var left: Int = -1
        var right: Int = -1
        val hasTwo : Boolean
            get() = left != -1 && right != -1

        fun take(value: Int) {
            if (left == -1) {
                left = value
            } else {
                right = value
            }
        }
    }

    val botList = mutableMapOf<Int, Bot>()
    val outputList = mutableMapOf<Int, Int>()

    fun partOne(instructions: List<String>, targetChips: Pair<Int, Int>): Int {
        val targets = listOf(targetChips.first, targetChips.second)
        for (instruction in instructions) {
            setupRx.matchEntire(instruction)?.let { mr ->
                val (value, botNum) = mr.destructured.toList().map { i -> i.toInt() }
                (botList.getOrPut(botNum) { Bot(botNum) }).apply {
                    take(value)
                }
                continue
            }
            botBotRx.matchEntire(instruction)?.let { mr ->
                val (botNum, low, high) = mr.destructured.toList().map { i -> i.toInt() }
                (botList.getOrPut(botNum) { Bot(botNum) }).apply {
                    lowTarget = low
                    highTarget = high
                }
                continue
            }
            outBotRx.matchEntire(instruction)?.let { mr ->
                val (botNum, low, high) = mr.destructured.toList().map { i -> i.toInt() }
                (botList.getOrPut(botNum) { Bot(botNum) }).apply {
                    lowTarget = low
                    highTarget = high
                    lowIsOutput = true
                }
                continue
            }
            botOutRx.matchEntire(instruction)?.let { mr ->
                val (botNum, low, high) = mr.destructured.toList().map { i -> i.toInt() }
                (botList.getOrPut(botNum) { Bot(botNum) }).apply {
                    lowTarget = low
                    highTarget = high
                    highIsOutput = true
                }
                continue
            }
            outOutRx.matchEntire(instruction)?.let { mr ->
                val (botNum, low, high) = mr.destructured.toList().map { i -> i.toInt() }
                (botList.getOrPut(botNum) { Bot(botNum) }).apply {
                    lowTarget = low
                    highTarget = high
                    lowIsOutput = true
                    highIsOutput = true
                }
                continue
            }
        }
        processChips(botList)
        return botList.values.first { it.left in targets && it.right in targets }.botNum.also {
            println("Found target bot: $it")
        }
    }

    fun processChips(botList: Map<Int, Bot>) {
        val queue = botList.toMutableMap()
        var current: Bot? = queue.values.first { it.hasTwo }
        while(current != null) {
            val (low,  lowOut) = minOf(current.left, current.right) to current.lowIsOutput
            val (high, highOut) = maxOf(current.left, current.right) to current.highIsOutput
            if (lowOut) {
                outputList[current.lowTarget] = low
            } else {
                queue[current.lowTarget]?.take(low)
            }
            if (highOut) {
                outputList[current.highTarget] = high
            } else {
                queue[current.highTarget]?.take(high)
            }
            queue.remove(current.botNum)

            current = queue.values.firstOrNull { it.hasTwo }
        }
    }

    fun partTwo() : Int {
        return (outputList[0]!! * outputList[1]!! * outputList[2]!!).also {
            println("Product of Output 0, Output 1, Output 2: $it")
        }
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2016/ten.txt", sampleMode)?.let { instructions ->
            partOne(instructions, Pair(17, 61))
            partTwo()
        }
    }
}
