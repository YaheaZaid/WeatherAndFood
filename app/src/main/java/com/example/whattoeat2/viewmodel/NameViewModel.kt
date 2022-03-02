package com.example.whattoeat2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.whattoeat2.model.Name
import com.example.whattoeat2.view.NameRepository
import com.example.whattoeat2.view.PostsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NameViewModel(application:Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<Name>>
    private val repository : NameRepository
     init {
        val nameDao = PostsDatabase.getInstance(application)!!.postsDao()
        repository = NameRepository(nameDao)
        readAllData = repository.readAllData
    }
    fun addName(name:Name){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addName(name)
        }

    }
    fun deleteName(name: Name){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteName(name)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllUser()
        }
    }
}