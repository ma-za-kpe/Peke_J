package com.maku.pekej.ui.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.pekej.R
import timber.log.Timber
import androidx.databinding.BindingAdapter
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso


class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The current word live data, private to onlny he view model, which is allowed to change it(internal version)
    private var _eventGameFinish = MutableLiveData<Boolean>()
    //intorduce the LiveData (external version)
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    // The current word live data, private to onlny he view model, which is allowed to change it(internal version)
    private var _mage = MutableLiveData<Int>()
    //intorduce the LiveData (external version)
    val image : LiveData<Int>
        get() = _mage

    // The current word live data, private to onlny he view model, which is allowed to change it(internal version)
    private var _word = MutableLiveData<String>()
    //intorduce the LiveData (external version)
    val word : LiveData<String>
        get() = _word

    // The current score live data, private to only he view model, which is allowed to change it
    private var _score = MutableLiveData<Int>()
    //intorduce the LiveData
    val score : LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // The list of images - the front of the list is the next word to guess
    private lateinit var imageList: MutableList<Int>

    init {
        Timber.i("Game viewmodel created ...")
        resetList()
        resetImageList()
        nextWord()
        _score.value = 0
        _word.value = ""
        _mage.value = 0
        _eventGameFinish.value = false
        //timer
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }
        timer.start()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "one",
            "two",
            "zebra",
            "cow",
            "squat"
        )
        wordList.shuffle()
    }

    /**
     * Resets the list of images and randomizes the order
     */
    private fun resetImageList() {
        imageList = mutableListOf(
            R.drawable.ic_launcher_background,
            R.drawable.p,
            R.drawable.me,
            R.drawable.lion,
            R.drawable.moi
        )
        imageList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            // gameFinished() should happen here
//            _eventGameFinish.value = true
            resetList()
        }else{
        _word.value = wordList.removeAt(0)}

    }

    /**
     * Moves to the next image in the list
     */
    private fun nextimage() {
        //Select and remove a word from the list
        if (imageList.isEmpty()) {
            // gameFinished() should happen here
//            _eventGameFinish.value = true
            resetImageList()
        }else{
            _mage.value = imageList.removeAt(0)}

    }

    /** Methods for buttons presses add null safety checks, then call the minus and plus functions, respectively. For example:**/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
        nextimage()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
        nextimage()
    }


    /** Methods to handle the game finished event  **/
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        Timber.i("Gameviewmodel destroyed ...")
        super.onCleared()
        timer.cancel()
    }
}
