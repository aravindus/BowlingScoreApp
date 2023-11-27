package com.aravind.bowlingscoreapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravind.bowlingscoreapp.game.BowlingScoreGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameScoreViewModel(application: Application) : AndroidViewModel(application) {
    var mScoreLiveData: MutableLiveData<Int> = MutableLiveData()
    var mErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var mInProgress: MutableLiveData<Boolean> = MutableLiveData()

    fun getScoreFromData(gameData: String) {
        mInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            mScoreLiveData.postValue(processGame(gameData))
            mInProgress.postValue(false)
        }
    }

    private fun processGame(gameData: String): Int? {
        return try {
            val bowlingScoreGame = BowlingScoreGame()
            var scores = gameData.split(",")
            for (scoreHit in scores) {
                bowlingScoreGame.roll(scoreHit.trim().toInt())
            }
            bowlingScoreGame.calculatedScore
        } catch (e: Exception) {
            mErrorLiveData.postValue(true)
            return 0
        }
    }
}