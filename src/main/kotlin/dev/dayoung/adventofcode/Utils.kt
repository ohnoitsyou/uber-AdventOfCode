package dev.dayoung.adventofcode

class Utils {
    companion object {
        private fun readResourceFile(filename: String) =
            this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines()

        fun readInputResource(sampleMode: Boolean = false, filename: String) =
            when (sampleMode) {
                false -> readResourceFile("/input/$filename")
                true -> readResourceFile("/sample/$filename")
            }
    }
}
