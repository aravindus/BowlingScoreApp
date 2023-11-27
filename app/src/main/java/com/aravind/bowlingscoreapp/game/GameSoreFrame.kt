package com.aravind.bowlingscoreapp.game

import java.util.stream.IntStream

class GameSoreFrame constructor(lastFrame: Boolean = false) {
    var isLastFrame: Boolean = false
    private val rolls: IntArray = if (lastFrame) IntArray(3) else IntArray(2)
    private var pinsHitIndex = 0
    var isSpare = false
    var isStrike = false
    var isDone = false
    val score: Int get() = IntStream.of(*rolls).sum()

    fun roll(pinsHits: Int) {

        rolls[pinsHitIndex] = pinsHits
        val firstRoll = pinsHitIndex == 0
        val lastRoll = pinsHitIndex == rolls.size - 1

        isStrike = firstRoll && score == 10
        isSpare = lastRoll && score == 10

        isDone = if (isLastFrame) {
            lastRoll
        } else {
            isStrike || isSpare || lastRoll
        }
        pinsHitIndex++
    }

    fun getRollHitScore(rolls: Int): Int {
        return IntStream.of(*this.rolls).limit(rolls.toLong()).sum()
    }
}


