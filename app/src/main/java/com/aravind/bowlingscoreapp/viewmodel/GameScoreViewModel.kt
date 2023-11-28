package com.aravind.bowlingscoreapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravind.bowlingscoreapp.game.BowlingGameScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Game viewModel : A lifecycle component to provide the data to the
 * presentation layer check the update on each time to the server
 */
class GameScoreViewModel(application: Application) : AndroidViewModel(application) {
    // LiveData to store the Score
    var mScoreLiveData: MutableLiveData<Int> = MutableLiveData()

    // LiveData to store the Error Status
    var mErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    // LiveData to store the Progress status
    var mInProgress: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Get the score from the input frames
     * @param pinsScored : no of pins scored in each frame
     */
    fun getScoreFromData(pinsScored: String) {
        mInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            mScoreLiveData.postValue(processGame(pinsScored))
            mInProgress.postValue(false)
        }
    }

    /**
     * Method to split the input frames from comma separated string to Integer
     */
    private fun processGame(gameData: String): Int? {
        return try {
            val bowlingGameScore = BowlingGameScore()
            var scores = gameData.split(",")
            for (scoreHit in scores) {
                bowlingGameScore.roll(scoreHit.trim().toInt())
            }
            bowlingGameScore.calculatedScore
        } catch (e: Exception) {
            mErrorLiveData.postValue(true)
            return 0
        }
    }
}