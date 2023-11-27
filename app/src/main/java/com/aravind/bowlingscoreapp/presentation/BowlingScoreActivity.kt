package com.aravind.bowlingscoreapp.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aravind.bowlingscoreapp.R
import com.aravind.bowlingscoreapp.utils.Constants.Companion.PERFECT_SCORE
import com.aravind.bowlingscoreapp.utils.Utils.Companion.hideSoftKeyboard
import com.aravind.bowlingscoreapp.viewmodel.GameScoreViewModel

class BowlingScoreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBtnScore: Button
    private lateinit var mETScore: EditText
    private lateinit var mtvFinalScore: TextView
    private lateinit var mtvProgress: TextView
    private lateinit var mtvStatusMessage: TextView

    private lateinit var mGameScoreViewModel: GameScoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bowling_score)
        initViews()
        initViewModelObserver()
    }

    private fun initViews() {
        mBtnScore = findViewById(R.id.btnScore)
        mETScore = findViewById(R.id.edtScores)
        mtvFinalScore = findViewById(R.id.tvFinalScore)
        mtvProgress = findViewById(R.id.tvProgress)
        mtvStatusMessage = findViewById(R.id.tvStatusMessage)
        mBtnScore.setOnClickListener(this)
    }

    private fun initViewModelObserver() {
        mGameScoreViewModel = ViewModelProvider(this)[GameScoreViewModel::class.java]
        mGameScoreViewModel.mScoreLiveData.observe(this, onScoreCalculated())
        mGameScoreViewModel.mErrorLiveData.observe(this, onErrorReceived())
        mGameScoreViewModel.mInProgress.observe(this, scoreCalculationInProgress())
    }

    private fun scoreCalculationInProgress(): Observer<Boolean> = Observer {
        showProgressInUI(it)
    }

    private fun onErrorReceived(): Observer<Boolean> = Observer {
        showMessageInUI(getString(R.string.invalid_input))
    }

    private fun onScoreCalculated(): Observer<Int> = Observer { score ->
        showScoreInUI("$score")
        if (score >= PERFECT_SCORE) {
            showMessageInUI(getString(R.string.perfect_game_message))
        }
    }

    private fun showProgressInUI(show: Boolean) {
        mtvProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun showMessageInUI(message: String?) {
        mtvStatusMessage.text = message
        mtvStatusMessage.visibility = if (!message.isNullOrBlank()) View.VISIBLE else View.GONE
    }

    private fun getCalculatedScore() {
        if (mGameScoreViewModel.mInProgress.value == true) {
            return
        }
        val scoreHits = mETScore.text.toString().trim()
        if (scoreHits.isNullOrBlank()) {
            showMessageInUI(getString(R.string.invalid_input))
            return
        }
        mGameScoreViewModel.getScoreFromData(scoreHits)
    }

    private fun showScoreInUI(score: String) {
        mtvFinalScore.text = score
    }

    override fun onClick(view: View?) {
        val handler = Handler(Looper.getMainLooper()) {
            showScoreInUI("0")
            showMessageInUI(null)
            getCalculatedScore()
            true
        }
        hideSoftKeyboard(this, mETScore, handler)
    }

}