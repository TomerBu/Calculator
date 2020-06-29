package edu.tomerbu.fragviewmodel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainViewModel : ViewModel() {

    private val users: MutableLiveData<List<User>> by lazy {
        val data = MutableLiveData<List<User>>()

        viewModelScope.launch {
            loadUsers()
        }

        return@lazy data
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private suspend fun loadUsers() {
        GlobalScope.launch(Dispatchers.IO){

        }
        // Do an asynchronous operation to fetch users.
        withContext(Dispatchers.IO) {
            users.postValue(loadUsersAsync().get())
        }


    }

    val ex = Executors.newSingleThreadExecutor()

    private fun loadUsersAsync(): Future<List<User>> {
        return ex.submit(Callable {
            Thread.sleep(5000)
            listOf(User(("Moshe")))
        })
    }
}
