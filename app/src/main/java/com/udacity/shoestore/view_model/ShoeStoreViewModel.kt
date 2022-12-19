package com.udacity.shoestore.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.shoe.Shoe

class ShoeStoreViewModel : ViewModel() {
    //adding a shoe
    private val _shoeList = MutableLiveData<List<Shoe>>()
    val shoeList: LiveData<List<Shoe>>
        get() = _shoeList

    private val shoes = mutableListOf<Shoe>()

    var name = MutableLiveData<String>()
    var brand = MutableLiveData<String>()
    var size = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var about = MutableLiveData<String>()

    /**
     * getting the shoe data from add shoe fragment
     * and sending it to the shoe list fragment where it will be added as a new shoe
     */
    private fun getShoe(): Shoe? {
        val name = name.value
        val brand = brand.value
        val size = size.value
        val price = price.value
        val about = about.value
        return if (name.isNullOrBlank() || brand.isNullOrBlank() || size.isNullOrBlank() || price.isNullOrBlank() || about.isNullOrBlank())
            null
        else
            Shoe(name, brand, size, price, about)

    }

    /**
     * adding the shoe to the view when the add shoe button is pressed
     */
    fun addingShoe() {
        getShoe()?.let {
            shoes.add(it)
            _shoeList.value = shoes
        }
    }

    /**
     * clearing the fields of the add shoe
     * fragment so that when adding a new shoe the old data is not there
     */
    override fun onCleared() {
        super.onCleared()
        fun clean() {
            name.value = ""
            brand.value = ""
            size.value = ""
            price.value = ""
            about.value = ""
        }
        clean()
    }

    //signing the user
    private val passwords = mutableMapOf<String, String>()
    private var user: String? = null

    /**
     * for signing up the user if they were not signed in before
     * and saving their data so that they can sign in when ever they want
     */
    fun signup(email: String, pass: String): Boolean {
        if (email in passwords) {
            return false
        }
        passwords[email] = pass
        return true
    }

    /**
     * for signing in the user if they previously signed in
     */
    fun signin(email: String, pass: String): Boolean {

        if (passwords[email] != pass) {
            return false
        }
        user = email
        return true
    }
}