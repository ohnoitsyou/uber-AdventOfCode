package dev.dayoung.adventofcode.twentytwentythree

import dev.dayoung.adventofcode.PuzzleSolution
import dev.dayoung.adventofcode.Utils
import org.springframework.stereotype.Component

@Component
class Day202307: PuzzleSolution(2023, 7, true) {
    enum class Card(val v: String) {
        ACE("A"), KING("K"), QUEEN("Q"), JACK("J"), TEN("T"), NINE("9"), EIGHT("8"), SEVEN("7"), SIX("6"), FIVE("5"), FOUR("4"), THREE("3"), TWO("2");
    }

     enum class HANDTYPE {
         FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH
     }

    data class Hand(val cards: List<Card>, val bid: Int) {
        val sortedHand = cards.sorted()
        val type = {
            val c = cards.map { card ->
                cards.count { it == card }
            }
            println(c)
        }
    }

    fun partOne(hands: List<Hand>): Int {
        hands.forEach { it.type() }
        return 0
    }

    override fun solve(sampleMode: Boolean) {
        println("2023 Day 07")
        Utils.readInputResource(sampleMode, "2023/seven.txt")?.let {
            val hands = it.map { handBid ->
                val hb = handBid.split(" +".toRegex())
                hb.first().trim() to hb.last().toInt()
            }.map { (hand, bid) ->
                Hand(hand.map { c -> Card.entries.first { c.toString() == it.v }}, bid)
            }
            println("Part One: ${partOne(hands)}")
        }
    }
}
