package com.udacity.shoestore.fragments


import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.AddedShoeBinding
import com.udacity.shoestore.databinding.ShoelistFragmentBinding
import com.udacity.shoestore.shoe.Shoe
import com.udacity.shoestore.view_model.ShoeStoreViewModel
import timber.log.Timber


class ShoeListFragment : Fragment() {
    private lateinit var binding: ShoelistFragmentBinding
    private val viewModel: ShoeStoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShoelistFragmentBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if the user is trying to add empty data it wont let the do so other wise their data is added
        viewModel.shoeList.observe(viewLifecycleOwner) {
            //Timber.tag("ShoeListFragment").i(it.toString())
            if (it.isNotEmpty()) {
                binding.introText.visibility = View.GONE
                for (shoe in it) {
                    addShoe(shoe)
                }
            } else {
                showSnackbar("Empty Data")
            }
        }

        binding.buttonAdd.setOnClickListener {
            this.findNavController().navigate(
                ShoeListFragmentDirections.actionShoeListFragmentToAddShoeFragment()
            )
        }
    }

    /**
     * adding the shoe to the shoe list using binding
     */
    private fun addShoe(shoe: Shoe) {
        val itemBinding = AddedShoeBinding.inflate(LayoutInflater.from(context))
        itemBinding.shoe = shoe
        binding.linearShoelist.addView(itemBinding.root)
    }

    //adding menu so that the user can logout freely
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.store_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.signout ->
                    findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToSignInFragment())
                else -> return false
            }
            return true
        }
    }

    /**
     * snackbar is used instead of toast as it has a better look,
     * where it appears from the bottom
     */
    private fun showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireView(), message, duration).show()
    }

}