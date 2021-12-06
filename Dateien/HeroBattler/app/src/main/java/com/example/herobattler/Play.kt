package com.example.herobattler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.herobattler.databinding.FragmentHowToPlayBinding
import com.example.herobattler.databinding.FragmentPlayBinding


class Play : Fragment() {
    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //adapt also this name (the class will be autogenerated)
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReturnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_play_to_mainMenu)
        }
    }
}