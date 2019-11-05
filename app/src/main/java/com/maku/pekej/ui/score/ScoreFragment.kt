package com.maku.pekej.ui.score

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.maku.pekej.R
import com.maku.pekej.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    companion object {
        fun newInstance() = ScoreFragment()
    }

    private lateinit var viewModel: ScoreViewModel
    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.score_fragment, container, false)

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        // Add observer for score
        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        binding.sViewModel = viewModel
        binding.setLifecycleOwner(this)


        // Navigates back to title when button is pressed
        viewModel.eventPlayAgain.observe(this, Observer { playAgain ->
            if (playAgain) {
//                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_nav_home)
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }

}
