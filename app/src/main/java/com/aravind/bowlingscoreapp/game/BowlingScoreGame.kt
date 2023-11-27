package com.aravind.bowlingscoreapp.game

class BowlingScoreGame {

    private val gameSoreFrames: Array<GameSoreFrame> = arrayOf(
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(),
        GameSoreFrame(true),
    )
    private var frameIndex = 0
    val calculatedScore: Int
        get() {
            var score = 0
            for (i in gameSoreFrames.indices) {
                var bonusScore = 0
                if (gameSoreFrames[i].isLastFrame && gameSoreFrames[i - 1].isStrike) {
                    return gameSoreFrames[i].score.let { score += it; score }
                } else if (gameSoreFrames[i].isStrike) {
                    bonusScore = addScoreForStrike(i)
                } else if (gameSoreFrames[i].isSpare) {
                    bonusScore = addScoreForSpare(i)
                }
                score += gameSoreFrames[i].score + bonusScore
            }
            return score

        }

    private fun addScoreForSpare(currentFrameIndexSpare: Int): Int {
        return gameSoreFrames[currentFrameIndexSpare + 1].getRollHitScore(1)
    }

    private fun addScoreForStrike(currentFrameIndexStrike: Int): Int {
        var bonusScore: Int
        val nextGameFrame: GameSoreFrame = gameSoreFrames[currentFrameIndexStrike + 1]
        when {
            nextGameFrame.isLastFrame -> {
                bonusScore = nextGameFrame.getRollHitScore(2)
            }

            nextGameFrame.isStrike -> {
                val frameAfterNextGameFrame: GameSoreFrame =
                    gameSoreFrames[currentFrameIndexStrike + 2]
                bonusScore = frameAfterNextGameFrame.getRollHitScore(1)
                bonusScore += nextGameFrame.score
            }

            else -> {
                bonusScore = nextGameFrame.score
            }
        }
        return bonusScore
    }

    fun roll(pinsHit: Int?) {
        if (gameSoreFrames[frameIndex].isDone) {
            frameIndex++
        }
        if (null != pinsHit) {
            gameSoreFrames[frameIndex].roll(pinsHit)
        }
    }

}