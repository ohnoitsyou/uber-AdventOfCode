package dev.dayoung.adventofcode.benchmarks

import dev.dayoung.adventofcode.Utils
import dev.dayoung.adventofcode.toLongRanges
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Blackhole
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import org.openjdk.jmh.annotations.Level
import java.util.concurrent.TimeUnit

@org.openjdk.jmh.annotations.State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class Day202502Benchmark {

    private lateinit var inputData: List<LongRange>
    private lateinit var content: String
    @Setup(Level.Trial)
    fun setup() {
        inputData = Utils.readInputResource("2025/02.txt", true)?.first()?.split(",")?.toLongRanges() ?: emptyList()
        content = Utils.readInputResource("2025/02.txt", true)?.first() ?: ""
    }

    @Benchmark
    fun toRanges(bh: Blackhole) {
        val input = content.split(",").toLongRanges()
        bh.consume(input)
    }

    @Benchmark
    fun flatMapFilter(bh: Blackhole) {
        val result = inputData.flatMap { range ->
            range.filter {
                val value = it.toString()
                (1 .. (value.length / 2)).any { idx ->
                    Regex("""^(${value.take(idx)})+$""").containsMatchIn(value.drop(idx))
                }
            }
        }.sum()
        bh.consume(result)
    }

    @Benchmark
    fun flatMapSeq(bh: Blackhole) {
        val result = inputData.flatMap { range -> range.toList() }.asSequence().filter {
                val value = it.toString()
                (1..(value.length / 2)).any { idx ->
                    Regex("""^(${value.take(idx)})+$""").containsMatchIn(value.drop(idx))
                }
            }.sum()
        bh.consume(result)
    }

    @Benchmark
    fun flatMapWhole(bh: Blackhole) {
        val result = inputData.flatMap { range -> range.toList() }.filter {
            val value = it.toString()
            (1..(value.length / 2)).any { idx ->
                Regex("""^(${value.take(idx)})+$""").containsMatchIn(value.drop(idx))
            }
        }.sum()
        bh.consume(result)
    }
}
