package dev.dayoung.adventofcode.benchmarks

import dev.dayoung.adventofcode.Utils
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@org.openjdk.jmh.annotations.State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class Day201607Benchmark {
    val hypernet = Regex("""(.)(?!\1)(.)\2\1(?=[^\[]*])""")
    val replace = Regex("""\[.*?]""")
    val pattern = Regex("""(.)(?!\1)(.)\2\1""")

    private lateinit var inputData: List<String>

    @Setup(Level.Trial)
    fun setup() {
        Utils.readInputResource(false, "2016/seven.txt")?.let {
            inputData = it
        } ?: run {
            inputData = emptyList()
        }
    }
    @Benchmark
    fun filterFirst(bh: Blackhole) {
        val result = inputData.filter {
                !hypernet.containsMatchIn(it)
            }.count {
                pattern.containsMatchIn(replace.replace(it, "----"))
            }
        bh.consume(result)
    }

    @Benchmark
    fun countAndCheck(bh: Blackhole) {
        val result = inputData.count {
            !hypernet.containsMatchIn(it) && pattern.containsMatchIn(replace.replace(it, "----"))
        }
        bh.consume(result)
    }

    @Benchmark
    fun useFlow(bh: Blackhole) = runBlocking {
        val result = inputData.asFlow().filter {
            !hypernet.containsMatchIn(it)
        }.count {
            pattern.containsMatchIn(replace.replace(it, "----"))
        }
        bh.consume(result)
    }
    @Benchmark
    fun useFlowFCountAndCheck(bh: Blackhole) = runBlocking {
        val result = inputData.asFlow().count {
            !hypernet.containsMatchIn(it) && pattern.containsMatchIn(replace.replace(it, "----"))
        }
        bh.consume(result)
    }
}
