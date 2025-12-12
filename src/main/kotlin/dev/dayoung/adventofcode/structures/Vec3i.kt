package dev.dayoung.adventofcode.structures

import kotlin.math.pow
import kotlin.math.sqrt

data class Vec3i(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vec3i): Vec3i { return Vec3i(x + other.x, y + other.y, z + other.z) }
    operator fun minus(other: Vec3i): Vec3i { return Vec3i(x - other.x, y - other.y, z - other.z) }

    fun eDistance(other: Vec3i): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return sqrt(dx.pow(2) + dy.pow(2) + dz.pow(2))
    }

    override fun toString(): String = "($x, $y, $z)"
}

fun List<String>.toVec3iList(sep: String = ","): List<Vec3i> {
    return map {
        val (x, y, z) = it.split(sep)
        Vec3i(x.toInt(), y.toInt(), z.toInt())
    }
}
