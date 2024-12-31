package dev.dayoung.adventofcode.structures

import io.github.oshai.kotlinlogging.KotlinLogging

class IntcodeComputer(private var program: List<String>) {
    val log = KotlinLogging.logger("IntcodeComputer")
    private var workingMemory: MutableList<String> = mutableListOf()

    private var eax = 0
    private val opcodes = mapOf(
        "1" to { codeIdx: Int ->
            val argsIdx = codeIdx + 1
            val (a, b, c) = workingMemory.subList(argsIdx, argsIdx + 3).map { it.toInt() }
            workingMemory[c] = "${workingMemory[a].toInt() + workingMemory[b].toInt()}"
            eax += 4
        },
        "2" to { codeIdx: Int ->
            val argsIdx = codeIdx + 1
            val (a, b, c) = workingMemory.subList(argsIdx, argsIdx + 3).map { it.toInt() }
            workingMemory[c] = "${workingMemory[a].toInt() * workingMemory[b].toInt()}"
            eax += 4
        },
        "99" to { _ ->
            eax = -1
        }
    )

    fun setup(noun: String, verb: String) {
        workingMemory[1] = noun
        workingMemory[2] = verb
        eax = 0
    }

    fun loadWorkingMemory() {
        workingMemory = program.toMutableList()
    }

    fun run(outputPosition: Int): Int {
        while(eax >= 0) {
            val opcode = workingMemory[eax]
            opcodes[opcode]!!.invoke(eax)
        }
        return workingMemory[outputPosition].toInt()
    }
}
