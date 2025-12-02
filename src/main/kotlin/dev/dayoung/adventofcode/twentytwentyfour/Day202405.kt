package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.cut
import dev.dayoung.adventofcode.splitFile
import org.springframework.stereotype.Component

data class Update(val pages: List<Int>)

@Component
class Day202405: PuzzleSolution(2024, 5) {
    private fun separateValidUpdates(rules: Map<Int, List<Int>>, updates: List<Update>): Pair<List<Update>, List<Update>> {
        return updates.partition { update ->
            update.pages.all { page ->
                val requirements = rules.getOrElse(page, ::emptyList).filter { it in update.pages }
                val pageIdx = update.pages.indexOf(page)
                requirements.all { update.pages.indexOf(it) > pageIdx }
            }
        }
    }
    fun partOne(updates: Pair<List<Update>, List<Update>>): Int {
        return updates.first.sumOf { update -> update.pages[update.pages.size / 2] }
    }

    fun partTwo(rules: Map<Int, List<Int>>, updates: Pair<List<Update>, List<Update>>): Int {
        return updates.second.map { update ->
            update.pages.sortedBy { rules.getOrElse(it, ::emptyList).filter { it in update.pages }.size }
        }.sumOf { it[it.size / 2] }
    }

    override fun solve(sampleMode: Boolean) {
        println("2024 - Day 05")
        Utils.readInputResource("input/2024/five.txt", sampleMode)?.let { lines ->
            val (rules, versions) = lines.splitFile { it.isNotBlank() }
            val pageRules = rules.cut("|")
                .map { it.first.toInt() to it.second.toInt() }
                .groupBy({ it.first }, { it.second })
            val pageUpdates: List<Update> = versions.filter(String::isNotBlank).map { update ->
                update.split(",")
                    .map { page -> page.trim() }
                    .filter(String::isNotBlank)
                    .map { page -> page.toInt() }
                    .let { pages -> Update(pages) }
            }
            val separatedUpdates = separateValidUpdates(pageRules, pageUpdates)
            println("Part One: ${partOne(separatedUpdates)}")
            println("Part Two: ${partTwo(pageRules, separatedUpdates)}")
        }
    }
}
