package com.maku.pekej.ui.game

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.maku.pekej.R
import com.maku.pekej.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    //binding variable
    private lateinit var binding: GameFragmentBinding

    private lateinit var gameviewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false)

        //construct a refence for the view model
        gameviewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        //this is to bind the viewmodeldirectly to the xml layout
        binding.gViewModel = gameviewModel
        binding.setLifecycleOwner(this)

        // UI controllers are where you'll set up the observation relationship
        gameviewModel.score.observe(this, Observer { newScore ->

            binding.scoreText.text = newScore.toString()

        })

//        gameviewModel.word.observe(this, Observer { newWord ->
//
//            binding.imageView2 = newWord.toString()
//
//        })

        return binding.root
    }

}
