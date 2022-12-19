package com.udacity.shoestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.databinding.SigninFragmentBinding
import com.udacity.shoestore.view_model.ShoeStoreViewModel

class SignInFragment : Fragment() {
    private val viewModel: ShoeStoreViewModel by activityViewModels()
    private lateinit var binding: SigninFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SigninFragmentBinding.inflate(inflater, container, false)
        //creating the functions of the sign buttons
        binding.signinButton.setOnClickListener { signin() }
        binding.signupButton.setOnClickListener { signup() }

        return binding.root
    }


    private fun signin() {
        val email = binding.emailEdit.text.toString()
        val pass = binding.passwordEdit.text.toString()

        if (viewModel.signin(email, pass)) {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToWelcomeFragment()
            )
        } else {
            showSnackbar("You entered incorrect info")
        }
    }

    private fun signup() {
        val email = binding.emailEdit.text.toString()
        val pass = binding.passwordEdit.text.toString()

        if (email.isEmpty() || pass.isEmpty()) {
            showSnackbar("Missing Email or Password")
        } else if (pass.length < 8) {
            showSnackbar("Your Password Must Be 8+ characters")
        } else if (viewModel.signup(email, pass)) {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToWelcomeFragment()
            )
        } else {
            showSnackbar("Already Signed up Before")
        }
    }

    private fun showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireView(), message, duration).show()
    }
}
