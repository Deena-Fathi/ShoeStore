package com.udacity.shoestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.AddshoeFragmentBinding
import com.udacity.shoestore.view_model.ShoeStoreViewModel


class AddShoeFragment : Fragment() {
    private lateinit var binding: AddshoeFragmentBinding
    private val viewModel: ShoeStoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddshoeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        //on pressing the add shoe button the view model will
        // call the adding shoe function and then goes back to the shoe list fragment
        // where the new shoe will be added
        binding.saveButton.setOnClickListener {
            viewModel.addingShoe()
            findNavController().popBackStack()
        }

    }


}
