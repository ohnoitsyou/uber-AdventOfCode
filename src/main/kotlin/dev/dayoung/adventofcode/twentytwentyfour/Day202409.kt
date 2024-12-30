package dev.dayoung.adventofcode.twentytwentyfour

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

fun main() {
    Day202409().solve(true)
}

@Component
class Day202409: PuzzleSolution(2024, 9, true) {
    sealed interface Block {
        val pos: Int
        val idx: Int
        val length: Int
    }
    private data class File(override val pos: Int, override val idx: Int, override val length: Int) : Block {
        override fun toString(): String  = "$idx".repeat(length)
    }
    private data class Free(override val pos: Int, override val idx: Int, override val length: Int) : Block {
        override fun toString(): String = ".".repeat(length)
    }

    private tailrec fun makeDisk3(input: String, fileFlag: Boolean = true, idx: Int = 0, pos: Int = 0, blocks: List<Block> = emptyList()): List<Block> {
        if(input.isEmpty()) return blocks
        val item = input.first().digitToInt()
        val newItem = if(fileFlag) File(pos, idx, item) else Free(pos, idx, item)
        val newIdx = if(!fileFlag) idx + 1 else idx
        return makeDisk3(input.drop(1), !fileFlag, newIdx, pos + item, blocks + newItem)
//        return if (fileFlag) {
//            makeDisk3(input, false, idx + 1, pos + item, blocks + File(pos, idx, item))
//        } else {
//            makeDisk3(input, true, idx, pos + item, blocks + Free(pos, idx, item))
//        }
    }
    private tailrec fun makeDisk2(input: String, idx: Int = 0, pos: Int = 0, blocks: List<Block> = emptyList()): List<Block> {
        if(input.isEmpty()) return blocks
        val item = input.first()
        return makeDisk2(
            input.drop(1),
            idx + 1,
            idx + item.digitToInt(),
            blocks + if(idx % 2 == 0) File(pos, idx, item.digitToInt()) else Free(pos, idx, item.digitToInt()))
    }

    private fun makeDisk(input: String): List<Block> {
        val i = if(input.length % 2 != 0) input + "0" else input
        return buildList {
            i.toList().chunked(2).forEachIndexed { idx, (f, fs) ->
                repeat(f.digitToInt())  { add(File(idx, idx, 1)) }
                repeat(fs.digitToInt()) { add(Free(idx, idx, 1)) }
            }
        }
    }

    private fun partOne(input: List<Block>): Long {
        return compress(input.toMutableList(), 0, input.lastIndex, 0)
    }

    private fun partTwo(input: List<Block>): Long {

//        log.info { input.joinToString("") }
        val disk = compress2(input.toMutableList())
//        return 0L
        return disk.withIndex().filter { it.value is File }.sumOf { f -> (0 until f.value.length).sumOf { i -> (f.value.pos + i) * (f.value.idx / 2)}.toLong() }
    }

    private tailrec fun compress2(disk: MutableList<Block>, moveIter:ListIterator<Block> = disk.reversed().listIterator()): List<Block> {
        if(!moveIter.hasNext()) return disk.filter { it.length != 0 }
        return when(val item = moveIter.next()) {
            is File -> {
                val dest = disk.withIndex().firstOrNull { (_, it) -> it is Free && it.pos < item.pos && it.length >= item.length }
                val nd = disk.toMutableList()
                if(dest != null) {
                    val idx = dest.index
                    nd.add(idx, item)
                    nd[idx + 1] = Free(dest.value.pos, dest.value.idx, dest.value.length - item.length)
                    nd.asReversed()[(disk.lastIndex - moveIter.previousIndex()).coerceAtLeast(0)] = Free(0, 0, item.length)
                }
                compress2(nd, moveIter)
            }
            is Free -> compress2(disk, moveIter)
        }
    }

    private tailrec fun compress(diskString: List<Block>, curIdx: Int, replaceIdx: Int, runningTotal: Long): Long {
        if(curIdx > replaceIdx) return runningTotal
        return when(diskString[curIdx]) {
            is File -> {
                val value = (diskString[curIdx] as File).idx.toLong() * curIdx
                compress(diskString, curIdx + 1, replaceIdx, runningTotal + value)
            }
            is Free -> {
                val value = (diskString[replaceIdx] as File).idx.toLong() * curIdx
                val ns = diskString.dropLast(1).dropLastWhile { it is Free }
                compress(ns, curIdx + 1, ns.lastIndex, runningTotal + value)
            }
        }
    }

    private val parser =  { input: List<String> -> makeDisk(input.first().trim()) }
    private val parser2 = { input: List<String> -> makeDisk2(input.first().trim()) }
    private val parser3 = { input: List<String> -> makeDisk3(input.first().trim()) }

    override fun solve(sampleMode: Boolean) {
        log.info { "2024 - Day 09" }
//        Utils.readInputResource(sampleMode, "2024/nine.txt")?.let { parser(it) }?. let { input ->
//            log.info { "Part One: ${partOne(input)}" }
//        }
        Utils.readInputResource(sampleMode, "2024/nine.txt")?.let { parser3(it) }?. let { input ->
            log.info { "Part Two: ${partTwo(input)}" }
        }
    }
}
// attempt 1:[x] 1325578889408
// attempt 2:[x] 1331482983958
// attempt 3:[x] 1332845373578
// attempt 4:[x] 1333081724178


// correct:      6360363199987
