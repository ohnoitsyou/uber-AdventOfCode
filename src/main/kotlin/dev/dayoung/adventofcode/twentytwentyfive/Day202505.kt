package dev.dayoung.adventofcode.twentytwentyfive

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.splitFile
import dev.dayoung.adventofcode.toLongRanges
import org.springframework.stereotype.Component
import kotlin.comparisons.thenBy

@Component
class Day202505 : PuzzleSolution(2025, 5) {

    fun partOne(ranges: List<LongRange>, ids: List<Long>): Int {
        var freshCount = 0
        for(id in ids) {
            ranges.firstOrNull { id in it } ?.let { freshCount++ }
        }
        return freshCount
    }

    fun partTwo(ranges: List<LongRange>): Int {
        val splitRanges = mutableListOf<LongRange>()
        val sortedRanges = ranges.sortedWith(compareBy<LongRange> { it.first }.thenBy { it.last })
        log.info { "All ranges: $sortedRanges" }
        log.info { "Checking range: ${sortedRanges.first()}"}
        if(sortedRanges.first().last in sortedRanges[1]) {
            log.info { "${sortedRanges.first().last} contained inside ${sortedRanges[1]}"}
            // is the end of the first range inside the second
            // |-------------|
            //          |--------|
            // |--------|----|---|
            splitRanges.add(LongRange(sortedRanges.first().first, sortedRanges[1].first))
            splitRanges.add(LongRange(sortedRanges[1].first, sortedRanges.first().last))
            splitRanges.add(LongRange(sortedRanges.first().last, sortedRanges[1].last))
        } else {
            log.info { "${sortedRanges.first()} is standalone"}
            splitRanges.add(sortedRanges.first())
        }
        val middleRanges = sortedRanges.subList(1, sortedRanges.lastIndex)
        log.info { "Middle ranges: $middleRanges" }
        middleRanges.forEachIndexed { idx: Int, range: LongRange ->
            if(range.last in sortedRanges[idx + 2]) {
//                // is the end of the first range inside the second
//                // |-------------|     <- Range
//                //          |--------| <- middleRanges[idx + 1]
//                // |--------|----|---|
                log.info { "${range.last} contained inside ${sortedRanges[idx + 2]}"}
                splitRanges.add(LongRange(range.first, sortedRanges[idx + 2].first))
                splitRanges.add(LongRange(sortedRanges[idx + 2].first, range.last))
                splitRanges.add(LongRange(range.last, sortedRanges[idx + 2].last))
            } else {
                log.info { "$range is standalone"}
                splitRanges.add(range)
            }
        }
        if(sortedRanges.last().first in sortedRanges[sortedRanges.lastIndex - 1]) {
            log.info { "${sortedRanges.last().first} contained inside range: ${sortedRanges[sortedRanges.lastIndex - 1]}" }
            //          |--------| <- sortedRanges.last
            // |-------------|     <- sortedRanges[sortedRanges.lastIndex - 1]
            // |--------|----|---|
            splitRanges.add(LongRange(sortedRanges[sortedRanges.lastIndex - 1].first, sortedRanges.last().first))
            splitRanges.add(LongRange(sortedRanges.last().first, sortedRanges[sortedRanges.lastIndex - 1].last))
            splitRanges.add(LongRange(sortedRanges[sortedRanges.lastIndex - 1].last, sortedRanges.last().last))
        } else {
            log.info { "${sortedRanges.last()} is standalone"}
        }
        log.info { "Final non-overlapping ranges: $splitRanges" }
//        log.info { splitRanges.flatMap { it.toList() }.toSet() }
//        log.info { "[3, 4, 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]" }
        log.info { "Folded: ${splitRanges.fold(0L) { acc, range -> acc + (range.last - range.first - 1)}}" }
        return 0
    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource("2025/05.txt", sampleMode)?.let { input ->
            val (ranges, ids) = input.splitFile { it != "" }.let { (r, i) ->
                r.toLongRanges() to i.drop(1).map { it.toLong() }
            }
            log.info { "Count of fresh items: ${partOne(ranges, ids)}" }
            println("Total fresh ids: ${partTwo(ranges)}")
//            log.info { "Total fresh ids: ${partTwo(ranges)}" }
        }
    }
}

fun main() {
    Day202505().solve()
}

// |---------| <- Range
//      |-----------| <- rContainsLast
// |----|----|------|
// range.first - rContainsLast.first
// rContainsLast.first - range.last
// range.last - rContainsLast.last

//
//          |---------| <- Range
// |-----------| <- rContainsFirst
// |--------|--|------|
// rContainsFirst.first - range.first
// range.first - rContainsFirst.last
// rContainsFirst.last - range.last

// 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
//       |---|
//                      |----------|
//                                       |-----------|
//                           |-----------------|
//       |---|          |----|-----|-----|-----|-----|
//        3-5           10-12 12-14 14-16 16-18 18-20
