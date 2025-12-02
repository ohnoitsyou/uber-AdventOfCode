package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component
import kotlin.time.measureTimedValue

@Component
class Day201607: PuzzleSolution(2016, 7) {
    val hypernet = Regex("""(.)(?!\1)(.)\2\1(?=[^\[]*])""")
    val replace = Regex("""\[.*?]""")
    val pt1Pattern = Regex("""(.)(?!\1)(.)\2\1""")
    val pt2Pattern = Regex("""(?=(.)(?!\1)(.)\1)""")

    fun partOne(input: List<String>): Int {
        return input.filter {
            !hypernet.containsMatchIn(it)
        }.count {
            pt1Pattern.containsMatchIn(replace.replace(it, "----"))
        }
    }

    fun partTwo(input:List<String>): Int {
        return input.map {
            replace.replace(it, "---")
        }.map {
            pt2Pattern.findAll(it)
        }.mapIndexed { idx, matches ->
            matches.any {
                val f = it.groups[1]?.value!!
                val s = it.groups[2]?.value!!
                Regex("""$s$f$s(?=[^\[\]]*])""").containsMatchIn(input[idx])
            }
        }.count { it }
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2016/seven.txt", sampleMode)?.let { input ->
            val d1 = measureTimedValue { partOne(input) }
            val d2 = measureTimedValue { partTwo(input) }
            println("Solved Part 1 in ${d1.duration.inWholeMilliseconds}ms with the value: ${d1.value}")
            println("Solved Part 2 in ${d2.duration.inWholeMilliseconds}ms with the value: ${d2.value}")
        }
    }
}
