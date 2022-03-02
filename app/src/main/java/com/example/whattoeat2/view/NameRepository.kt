package com.example.whattoeat2.view

import androidx.lifecycle.LiveData
import com.example.whattoeat2.model.Name

class NameRepository(private val postsDao:PostsDao) {
    val readAllData:LiveData<List<Name>> = postsDao.getAll()

    suspend fun addName(name:Name){
        postsDao.insertPost(name)

    }
    fun deleteName(name: Name){
        postsDao.deleteName(name)
    }
    fun deleteAllUser(){
        postsDao.deleteAll()
    }

}