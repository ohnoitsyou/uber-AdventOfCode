package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.Vec2i
import dev.dayoung.adventofcode.Vec2iV
import dev.dayoung.adventofcode.structures.Grid
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component


/*
 * There are definitely some improvements that can be made here.
 * I almost guarantee I am doing more conversions than necessary.
 */
@Component
class Day202410 : PuzzleSolution(2024, 10, true){
    fun partOne(map: Grid<Vec2iV<Int>>): Int {
        val trailheads = map.points.filter { it.value == 0 }
        val mappedTrails = trailheads.associateWith { map.followTrail(listOf(it), mutableListOf()).map { it.last() }.toSet() }
        val score = mappedTrails.map { it.value.count() }.sum()
        return score
    }

    fun partTwo(map: Grid<Vec2iV<Int>>): Int {
        val trailheads = map.points.filter { it.value == 0 }
        val mappedTrails = trailheads.associateWith { map.followTrail(listOf(it), mutableListOf()) }
        val rating = mappedTrails.map { it.value.count() }.sum()
        return rating
    }

    fun Grid<Vec2iV<Int>>.followTrail(trail: List<Vec2iV<Int>>, foundTrails: MutableList<List<Vec2iV<Int>>>): List<List<Vec2iV<Int>>> {
        val curPos = trail.last()
        if(curPos.value == 9) return foundTrails + listOf(trail)
        var localTrails = foundTrails
        for(dir in Vec2i.CARDINAL) {
            val nextPos = this[curPos.point + dir]
            if (nextPos.value == (curPos.value + 1)) {
                localTrails = followTrail(trail + nextPos, localTrails).toMutableList()
            }
        }
        return localTrails
    }

   val parser = { input: List<String> ->
       val pointList = input.flatMap { str -> str.toList().map { c -> c.digitToInt()}}
       val vec2iVList = pointList.mapIndexed { idx, v -> Vec2iV(Vec2i(idx % input.first().length, idx / input.size), v) }
       Grid(vec2iVList, input.first().length, input.size, Grid.ReturnDefault(Vec2iV(Vec2i(-1, -1), -1)))
   }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 10" }
        Utils.readInputResource(sampleMode, "2024/10.txt")?.let { parser(it) }?. let { grid ->
            log.info { "Part One; ${partOne(grid)}" }
            log.info { "Part Two; ${partTwo(grid)}" }
        }
    }
}
