package com.example.shamed.ui.main

import android.app.Application
import androidx.lifecycle.*

// What is this shorthand for?
// todo: read https://kotlinlang.org/docs/reference/classes.html
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // What is MutableLiveData, so you don't want the reference to change?
    // ans: MutableLiveData is a bucket for your values. It implements Observable, which allows
    // views to observe its state and change in response to it.
    // todo read: https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData
    // todo read: https://developer.android.com/topic/libraries/architecture/livedata#work_livedata

    // Why is it being in the viewModel make a difference?
    // ViewModels persist across orientation change. If you were to store a LiveData in an Activity
    // it would be destroyed on orientation change, so you lose state.

    private val repository: PlayerRepository

    // Goal: ensure that the object never changes
    val player : LiveData<Player>

    init {
        val playerDao = PlayerRoomDatabase.getDatabase(application).playerDao()
        repository = PlayerRepository(playerDao)
        player = repository.player
    }

    // TODO: next meeting learn how to persist data (using room)
        //1) Data inputted by user (assuming all data is mandatory)
        //2) daily inputs: i.e. breakfast, lunch, dinner (3 saved states for instance)

}
