package dev.dayoung.adventofcode.structures

typealias RegisterMap = MutableMap<String, AssembunnyComputer.Register>
typealias Instruction = Pair<AssembunnyComputer.Opcode, List<String>>

class AssembunnyComputer(instructions: List<String>) {

    private val instructions : List<Instruction> = instructions.parseInstructions()
    private var t = 0
    private var ip = 0

    enum class Opcode(val value: String, val rx: Regex, private val fn : (cpu: AssembunnyComputer, List<String>) -> Unit) {
        COPY("cpy", Regex("""cpy (\d+|[abcd]) ([abcd])"""), { cpu: AssembunnyComputer, args: List<String> ->
            require(args.size == 2) { "Expected [2] arguments. Got [${args.size}]" }
            val value = checkNotNull(args[0].toIntOrNull() ?: cpu[args[0]]?.value) { "${args[0]} not a number and register not found" }
            cpu[args[1]] = cpu[args[1]]!!.set(value)
        }),
        INC("inc", Regex("""inc ([abcd])"""), { cpu: AssembunnyComputer, args: List<String> ->
            require(args.size == 1) { "Expected [1] argument. Got [${args.size}]" }
            cpu[args[0]] = cpu[args[0]]!!.inc()
        }),
        DEC("dec", Regex("""dec ([abcd])"""), { cpu: AssembunnyComputer, args: List<String> ->
            require(args.size == 1) { "Expected [1] argument. Got [${args.size}]" }
            cpu[args[0]] = cpu[args[0]]!!.dec()
        }),
        JNZ("jnz", Regex("""jnz (\d+|[abcd]) (-?\d+)"""), { cpu: AssembunnyComputer, args: List<String> ->
            require(args.size == 2) { "Expected [2] arguments. Got [${args.size}]" }
            val value = checkNotNull(args[0].toIntOrNull() ?: cpu[args[0]]?.value) { "${args[0]} not a number and register not found"}
            if (value != 0) cpu.ip += (args[1].toInt() - 1) // -1 so that `invoke` can handle the ip
        });

        operator fun invoke(cpu: AssembunnyComputer, args: List<String>) {
            cpu.t++
            fn(cpu, args)
            cpu.ip++
        }
    }

    class Register(val value: Int = 0) {
        operator fun inc(): Register {
            return Register(value + 1)
        }

        operator fun dec(): Register {
            return Register(value - 1)
        }

        fun set(value: Int): Register {
            return Register(value)
        }
    }

    val registers: RegisterMap = mutableMapOf(
        "a" to Register(),
        "b" to Register(),
        "c" to Register(),
        "d" to Register())

    fun printRegisters() {
        println(registers.entries.joinToString(prefix = "[", postfix = "]") { (k, v) -> "$k: ${v.value}" })
    }

    fun List<String>.parseInstructions() : List<Instruction> = mapNotNull { inst ->
            Opcode.entries.firstOrNull { it.rx.containsMatchIn(inst) }?.let {
                it to (it.rx.matchEntire(inst)!!.destructured.toList())
            } ?: run {
                println("Instruction returned null: $inst")
                null
            }
        }

    operator fun get(register: String): Register? {
        return registers[register]
    }

    operator fun set(register: String, value: Register) {
        registers[register] = value
    }

    fun run() {
        while(ip <= instructions.lastIndex) {
            val (opcode, args) = instructions[ip]
            opcode(this, args)
        }
    }
}
