package dev.dayoung.adventofcode.twentyfifteen

import dev.dayoung.adventofcode.PuzzleSolution

typealias CityPair = Pair<String, String>


class Day09 : PuzzleSolution(2015, 9) {
    fun partOne(route: Map<CityPair, Int>, cities: List<String>) {
        val unvisitedSet = cities.toSet()
//        route
    }
    override fun solve(sampleMode: Boolean) {
        val cities = listOf("Faerun", "Tristram", "Tambi", "Norrath", "Snowdin", "Straylight", "AlphaCentauri", "Arbre")
        val routes = mutableMapOf<CityPair, Int>()
        routes["Faerun" to "AlphaCentauri"] = 3
        routes["Faerun" to "Tristram"]      = 65
        routes["Faerun" to "Snowdin"]       = 71
        routes["Faerun" to "Tambi"]         = 129
        routes["Faerun" to "Straylight"]    = 137
        routes["Faerun" to "Norrath"]       = 144
        routes["Faerun" to "Arbre"]         = 149

        routes["Tristram" to "Norrath"]       = 4
        routes["Tristram" to "Arbre"]         = 14
        routes["Tristram" to "AlphaCentauri"] = 55
        routes["Tristram" to "Tambi"]         = 63
        routes["Tristram" to "Faerun"]        = 65
        routes["Tristram" to "Snowdin"]       = 105
        routes["Tristram" to "Straylight"]    = 125

        routes["Tambi" to "AlphaCentauri"] = 22
        routes["Tambi" to "Snowdin"]       = 52
        routes["Tambi" to "Tristram"]      = 63
        routes["Tambi" to "Straylight"]    = 65
        routes["Tambi" to "Norrath"]       = 68
        routes["Tambi" to "Faerun"]        = 129
        routes["Tambi" to "Arbre"]         = 143

        routes["Norrath" to "Tristram"]      = 4
        routes["Norrath" to "Snowdin"]       = 8
        routes["Norrath" to "Straylight"]    = 23
        routes["Norrath" to "Tambi"]         = 68
        routes["Norrath" to "Arbre"]         = 115
        routes["Norrath" to "AlphaCentauri"] = 136
        routes["Norrath" to "Faerun"]        = 144

        routes["Snowdin" to "Norrath"]       = 8
        routes["Snowdin" to "Tambi"]         = 52
        routes["Snowdin" to "Faerun"]        = 71
        routes["Snowdin" to "AlphaCentauri"] = 84
        routes["Snowdin" to "Arbre"]         = 96
        routes["Snowdin" to "Straylight"]    = 101
        routes["Snowdin" to "Tristram"]      = 105

        routes["Straylight" to "Arbre"]         = 14
        routes["Straylight" to "Norrath"]       = 23
        routes["Straylight" to "Tambi"]         = 65
        routes["Straylight" to "Snowdin"]       = 101
        routes["Straylight" to "AlphaCentauri"] = 107
        routes["Straylight" to "Tristram"]      = 125
        routes["Straylight" to "Faerun"]        = 137

        routes["AlphaCentauri" to "Faerun"]     = 3
        routes["AlphaCentauri" to "Tambi"]      = 22
        routes["AlphaCentauri" to "Arbre"]      = 46
        routes["AlphaCentauri" to "Tristram"]   = 55
        routes["AlphaCentauri" to "Snowdin"]    = 84
        routes["AlphaCentauri" to "Straylight"] = 107
        routes["AlphaCentauri" to "Norrath"]    = 136

        routes["Arbre" to "Straylight"]    = 14
        routes["Arbre" to "Tristram"]      = 14
        routes["Arbre" to "AlphaCentauri"] = 46
        routes["Arbre" to "Snowdin"]       = 96
        routes["Arbre" to "Norrath"]       = 115
        routes["Arbre" to "Tabmi"]         = 143
        routes["Arbre" to "Faerun"]        = 149
        partOne(routes, cities)
    }
}