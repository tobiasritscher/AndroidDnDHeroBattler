package com.example.herobattler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.herobattler.databinding.FragmentCharactersBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Characters : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReturnCharacters.setOnClickListener {
            findNavController().navigate(R.id.action_characters_to_mainMenu)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}