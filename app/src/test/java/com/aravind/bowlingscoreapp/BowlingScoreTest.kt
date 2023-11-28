package com.aravind.bowlingscoreapp

import com.aravind.bowlingscoreapp.game.BowlingGameScore
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BowlingGameScoreTest {
    @Test
    fun testNotStarted() {
        val bowlingGame = BowlingGameScore()
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(0))
    }

    @Test
    fun testNoHits() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..10) {
            bowlingGame.roll(0)
            bowlingGame.roll(0)
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(0))
    }

    @Test
    fun testScoresAllOnes() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..10) {
            bowlingGame.roll(1)
            bowlingGame.roll(1)
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(20))
    }

    @Test
    fun testScoresOneStrike() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..10) {
            if (i == 1) {
                bowlingGame.roll(10) // strike
                continue
            }
            bowlingGame.roll(1)
            bowlingGame.roll(1)
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(30))
    }

    @Test
    fun testScoresOneSpare() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..10) {
            if (i == 1) {
                bowlingGame.roll(5)
                bowlingGame.roll(5) // spare
                continue
            }
            bowlingGame.roll(1)
            bowlingGame.roll(1)
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(29))
    }

    @Test
    fun testScoresTwoSpare() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..10) {
            if (i == 1) {
                bowlingGame.roll(5)
                bowlingGame.roll(5) // spare
                continue
            }
            if (i == 2) {
                bowlingGame.roll(5)
                bowlingGame.roll(5) // spare
                continue
            }
            bowlingGame.roll(1)
            bowlingGame.roll(1)
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(42))
    }

    @Test
    fun testPerfectGame() {
        val bowlingGame = BowlingGameScore()
        for (i in 1..12) {
            bowlingGame.roll(10) // strike
        }
        MatcherAssert.assertThat(bowlingGame.calculatedScore, CoreMatchers.equalTo(300))
    }
}