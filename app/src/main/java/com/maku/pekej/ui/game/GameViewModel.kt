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
            com.maku.pekej.R.drawable.ic_launcher_background,
            com.maku.pekej.R.drawable.p,
            com.maku.pekej.R.drawable.me,
            com.maku.pekej.R.drawable.lion,
            com.maku.pekej.R.drawable.moi
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

    override fun onCleared() {
        Timber.i("Gameviewmodel destroyed ...")
        super.onCleared()
    }
}
