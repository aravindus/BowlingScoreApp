package com.aravind.bowlingscoreapp.game

import java.util.stream.IntStream

/**
 * Class for Bowling Game each frame
 */
class GameSoreFrame constructor(lastFrame: Boolean = false) {

    // Boolean to store the last frame status
    var isLastFrame: Boolean = lastFrame

    // IntArray to for the no of rolls in a game
    private val rolls: IntArray = if (lastFrame) IntArray(3) else IntArray(2)

    // Variable to store the Index of the each frames hits
    private var pinsHitIndex = 0

    // Boolean variable to store the Strike
    var isSpare = false

    // Boolean variable to store the Strike
    var isStrike = false

    // Boolean variable to store the the last frame as done status
    var isDone = false

    /**
     * Function to iterate through the each pins in roll
     * @param pinsHits : An integer which store the no of pins that scored in each throw
     */
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

    // Integer to store the sum of rolls
    val score: Int get() = IntStream.of(*rolls).sum()

    /**
     * Method to get Score
     * @param rolls : No of rolls
     */
    fun getRollHitScore(rolls: Int): Int {
        return IntStream.of(*this.rolls).limit(rolls.toLong()).sum()
    }
}


