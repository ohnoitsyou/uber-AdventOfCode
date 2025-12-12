package dev.dayoung.adventofcode

import java.io.BufferedWriter
import java.io.File

class Utils {
    companion object {
        private fun readResourceFile(filename: String) =
            this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines()

        private fun writeCacheFile(filename: String): BufferedWriter {
            val userDir = System.getProperty("user.dir")
            val cacheDir = File(userDir, "cache")
            if (!cacheDir.exists()) cacheDir.mkdirs()
            return File(cacheDir, filename).bufferedWriter()
        }

        fun readInputResource(filename: String, sampleMode: Boolean = false) =
            when (sampleMode) {
                false -> readResourceFile("/input/$filename")
                true -> readResourceFile("/sample/$filename")
            }

        fun writeCacheResource(filename: String, sampleMode: Boolean = false): BufferedWriter =
            when(sampleMode) {
                false -> writeCacheFile("/input/$filename")
                true -> writeCacheFile("/sample/$filename")
            }
    }
}


fun <T> List<T>.toComboTripleLongBy(block: (T, T) -> Long): List<Triple<T, T, Long>> {
    return flatMapIndexed { idx, i -> this.subList(idx + 1, this.size).map { j -> Triple(i, j, block(i, j)) } }
}

fun String.cut(delimiter: String): Pair<String, String> {
    require(this.indexOf(delimiter) >= 0) { "Delimiter ($delimiter) must be in string ($this)" }
    return this.substringBefore(delimiter) to this.substringAfter(delimiter)
}

fun List<String>.cut(delimiter: String): List<Pair<String, String>> {
    return this.map { it.cut(delimiter) }
}

fun List<String>.splitFile(predicate: (String) -> Boolean): Pair<List<String>, List<String>> {
    return this.takeWhile(predicate) to this.dropWhile(predicate)
}

fun List<String>.toIntRanges(sep: String = "-"): List<IntRange> {
    return map { IntRange(it.substringBefore(sep).toInt(), it.substringAfter(sep).toInt()) }
}

fun List<String>.toLongRanges(sep: String = "-"): List<LongRange> {
    return map { LongRange(it.substringBefore(sep).toLong(), it.substringAfter(sep).toLong()) }
}

