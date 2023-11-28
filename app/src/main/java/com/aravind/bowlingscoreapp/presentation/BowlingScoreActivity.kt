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

/**
 * Bowling activity for showing the UI components and UI related activities
 */
class BowlingScoreActivity : AppCompatActivity(), View.OnClickListener {

    // Button variable for calculating the total score
    private lateinit var mBtnScore: Button

    // EditText to input the score
    private lateinit var mEtScore: EditText

    // TextView for view the final score
    private lateinit var mtvFinalScore: TextView

    // TextView to show progress message
    private lateinit var mtvProgress: TextView

    // TextView to show the status message if it is a perfect game
    private lateinit var mtvStatusMessage: TextView

    // ViewModel to calculate the score
    private lateinit var mGameScoreViewModel: GameScoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bowling_score)
        initViews()
        initViewModelObserver()
    }

    /**
     * View component initialisation
     */
    private fun initViews() {
        mBtnScore = findViewById(R.id.btnScore)
        mEtScore = findViewById(R.id.edtScores)
        mtvFinalScore = findViewById(R.id.tvFinalScore)
        mtvProgress = findViewById(R.id.tvProgress)
        mtvStatusMessage = findViewById(R.id.tvStatusMessage)
        mBtnScore.setOnClickListener(this)
    }

    /**
     * Initialize the observer and View model
     */
    private fun initViewModelObserver() {
        mGameScoreViewModel = ViewModelProvider(this)[GameScoreViewModel::class.java]
        mGameScoreViewModel.mScoreLiveData.observe(this, onScoreCalculated())
        mGameScoreViewModel.mErrorLiveData.observe(this, onErrorReceived())
        mGameScoreViewModel.mInProgress.observe(this, scoreCalculationInProgress())
    }

    /**
     * Override method for performing the button click action
     */
    override fun onClick(view: View?) {
        val handler = Handler(Looper.getMainLooper()) {
            showScoreInUI("0")
            showMessageInUI(null)
            getCalculatedScore()
            true
        }
        hideSoftKeyboard(this, mEtScore, handler)
    }

    /**
     * Method to show the progress message in UI
     */
    private fun scoreCalculationInProgress(): Observer<Boolean> = Observer {
        showProgressInUI(it)
    }

    /**
     * Method to show the error message in textview
     */
    private fun onErrorReceived(): Observer<Boolean> = Observer {
        showMessageInUI(getString(R.string.invalid_input))
    }

    /**
     * To show the calculated score
     */
    private fun onScoreCalculated(): Observer<Int> = Observer { score ->
        showScoreInUI("$score")
        if (score >= PERFECT_SCORE) {
            showMessageInUI(getString(R.string.perfect_game_message))
        }
    }

    /**
     * Method to show the text view for progress message
     */
    private fun showProgressInUI(show: Boolean) {
        mtvProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Method to show the status to text view
     */
    private fun showMessageInUI(message: String?) {
        mtvStatusMessage.text = message
        mtvStatusMessage.visibility = if (!message.isNullOrBlank()) View.VISIBLE else View.GONE
    }

    /**
     * Method to get the score from associated ViewModel
     */
    private fun getCalculatedScore() {
        if (mGameScoreViewModel.mInProgress.value == true) {
            return
        }
        val scoreHits = mEtScore.text.toString().trim()
        if (scoreHits.isBlank()) {
            showMessageInUI(getString(R.string.invalid_input))
            return
        }
        mGameScoreViewModel.getScoreFromData(scoreHits)
    }

    /**
     * Method to set the show the score in text view
     */
    private fun showScoreInUI(score: String) {
        mtvFinalScore.text = score
    }


}