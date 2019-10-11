package com.example.shamed.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// What is this shorthand for?
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // What is MutableLiveData, so you don't want the reference to change?
    // Why is it being in the viewModel make a difference?
    // Why do we want to observe this from other classes?

    private var repository = PlayerRepository()

    // Goal: ensure that the object never changes
    var weight:MutableLiveData<Int> = MutableLiveData()
    var height:MutableLiveData<Int> = MutableLiveData()
    var userName:MutableLiveData<String> = MutableLiveData()

    init {
        val playerDao = PlayerRoomDatabase.getDatabase(application, viewModelScope).playerDao()
        weight = repository.weight
        height = repository.height
        userName = repository.userName

    }

    // TODO: Implement the ViewModel
    // Save state
    // TODO: next meeting learn how to persist data (using room)
        //1) Data inputted by user (assuming all data is mandatory)
        //2) daily inputs: i.e. breakfast, lunch, dinner (3 saved states for instance)
        //3)

}
