package dev.dayoung.adventofcode.twentysixteen

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils

class Day201604 : PuzzleSolution(2016, 4) {

    // Rules:
    // Each room consists of an
    // encrypted name (lowercase letters separated by dashes) followed by a dash,
    // a sector ID,
    // and a checksum in square brackets.

    // A room is real (not a decoy) if the checksum is
    // the five most common letters in the encrypted name,
    // in order, with ties broken by alphabetization.

    val lineRegex = "([a-z-]+)-([0-9]+)\\[([a-z]+)]".toRegex()

    private data class Room(val name: String, val sectorId: Int, val checksum: String) {
        fun isValid(): Boolean {
            return name.replace("-", "").groupBy { it }.mapValues { it.value.size }.toList()
                .sortedWith(compareBy({0 - it.second }, { it.first }))
                .take(5)
                .map { it.first }
                .joinToString("") == checksum
        }
    }
    private fun partOne(input: List<String>) {
        val roomInput = input.mapNotNull { line ->
            lineRegex.find(line)?.groupValues?.let {
                Room(it[1], it[2].toInt(10), it[3])
            }
        }
        val one = roomInput.map { room ->
            print("Room isValid: ${room.isValid()} -> ")
            val (name, sectorId, checksum) = room
            val sanitizedName = name.replace("-", "")
            val frequencies = sanitizedName.associateWith { c -> sanitizedName.count { it == c } }
            val fList = frequencies.toList().sortedWith(compareBy({ 0 - it.second }, { it.first }))
            val topFive = fList.take(5)
            if (!checksum.all { name.contains(it) }) { return@map 0 }
            val idxDiffs = checksum.zipWithNext { a, b ->
                val bidx = fList.indexOfFirst { it.first == b }.takeIf { it >= 0 } ?: -1000
                val aidx = fList.indexOfFirst { it.first == a }.takeIf { it >= 0 } ?: -100
                bidx - aidx
            }
            val isValid = idxDiffs.all { it > 0 }
            println("entry: $name-$sectorId[$checksum] : $fList : $idxDiffs")
            if (isValid) sectorId else 0
//            sectorId
        }
        println("One sum: ${one.sum()}")
        val two = roomInput.filter { room -> room.isValid() }
        println("Two sum: $two")
//        val rm = roomInput.map { (name, sectorId, checksum) ->
//            val sanitizedName = name.replace("-", "")
//            val frequencies = sanitizedName.associateWith { c -> sanitizedName.count { it == c } }
//            val diffs = checksum.zipWithNext { a, b -> frequencies.getOrDefault(a, -1000) - frequencies.getOrDefault(b, -100) }
//            var isValid = diffs.none { it < 0 }
//            for((idx, diff) in diffs.withIndex()) {
//                if(diff > 0) continue
//                if(diff == 0) {
//                    val isAlphabetical = checksum[idx] < checksum[idx + 1]
////                    println("isAlphabetical: $isAlphabetical")
//                    if(!isAlphabetical) {
//                        isValid = false
//                    }
//                }
//            }
//            if(isValid) {
//                println("sus: $name-$sectorId[$checksum] : ${frequencies.toList().sortedBy { it.second }.reversed()} : $diffs")
//            }
//            if(isValid) sectorId else 0
//        }
        /*
        val roomMap = roomInput.map { (name, sectorId, checksum) ->
            val sanitizedName = name.replace("-", "")
            val frequencies = sanitizedName.associateWith { c -> sanitizedName.count { it == c } }
            val frequenciesList = frequencies.toList().sortedBy { (_, count) -> count }.reversed()
            var last = checksum[0] to frequencies.getOrDefault(checksum[0], 0)
            val isValid = checksum.drop(1).all { c ->
                val freq = frequencies.getOrDefault(c, 100)
                if(last.second > freq) {
                    true.also {
                        last = c to freq
                    }
                } else if(freq == last.second) {
                    (last.first < c).also {
                        last = c to freq
                    }
                } else {
                    println("Failed for reason: last: $last, c: $c, freq: $freq")
                    false.also {
                        last = c to freq
                    }
                }
            }
            if(isValid) sectorId else 0
        }
         */
        val sectorSum = one.sum()
        println("Sector sum: $sectorSum")
    }
//    val frequencies = groups[1].replace("-", "").toSet().groupBy {
//            letter -> groups[1].count { it == letter }
//    }
//    val checksum = groups[3]
//    //                println("Grouped letters: $groupedLetters")
//    val top5 = frequencies.keys.sortedDescending().take(5)

    private fun partTwo(input: String) {

    }

    override fun solve(sampleMode: Boolean) {
        Utils.readInputResource(sampleMode, "2016/four.txt")?.let {
            partOne(it)
        }
    }
}

fun main() {
    Day201604().solve(false)
}

// WRONG: 139516
// WRONG: 139200
// WRONG: 138277

   /*
//                var last = Triple(true, checksum[0], groups[1].count { it == checksum[0] })
            val (valid, _, _) = room.checksum.drop(1).fold(Triple(true, room.checksum, room.name.count { it == room.checksum[0] })) { acc, i ->
                val (valid, c, oldCount) = acc
                val iCount = room.name.count { it == i }
                if(oldCount > iCount) {
                    Triple(valid, i, iCount)
                } else if(oldCount == iCount) {
                    if(c < i) {
                        Triple(valid, i, iCount)
                    } else {
                        Triple(false, i, iCount)
                    }
                } else {
                    Triple(false, i, iCount)
                }
//                    if(!valid) {
//                        Triple(false, i, iCount)
//                    } else if(iCount == count) {
//                        if(c <= i) {
//                           Triple(true , i, iCount)
//                        } else {
//                           Triple(false, i, iCount)
//                        }
//                    } else if(count >= iCount && c < i) {
//                        Triple(true, i, iCount)
//                    } else {
//                        Triple(false, i, iCount)
//                    }
                if(valid) {
                    println("${str} is valid")
                    sum += sectorId
                } else {
                    println("${str} is invalid")
                }
                Triple(valid, sum, str)
            }
            println("Done: Sum: $sum")
            output.groupBy { it.first }.forEach { (status, entries) ->
                println("Status: $status")
                entries.forEach(::println)
            }
        }
    */

inline fun <T> Iterable<T>.allIndexed(predicate: (Int, T) -> Boolean): Boolean {
    if (this is Collection && isEmpty()) return true
    for ((idx, element) in this.withIndex()) if (!predicate(idx, element)) return false
    return true
}
