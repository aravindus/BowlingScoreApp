package com.aravind.bowlingscoreapp.game

/**
 *  A class to handle logic for calculating the overall score form the input frames
 */
class BowlingGameScore {

    // Variable to store the Game frame array
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
        GameSoreFrame(true)
    )

    // Variable to store the current frame index
    private var frameIndex = 0

    // Variable to store the calculated score from the frames
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

    /**
     * Method to add score when the frame is a Spare
     */
    private fun addScoreForSpare(currentFrameIndexSpare: Int): Int {
        return gameSoreFrames[currentFrameIndexSpare + 1].getRollHitScore(1)
    }

    /**
     * Method to add score when the frame is a Strike
     */
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

    /**
     * Method to get rolls in each frame
     * @param pinsHit : No of pins in each hits
     */
    fun roll(pinsHit: Int?) {
        if (gameSoreFrames[frameIndex].isDone) {
            frameIndex++
        }
        if (null != pinsHit) {
            gameSoreFrames[frameIndex].roll(pinsHit)
        }
    }

}