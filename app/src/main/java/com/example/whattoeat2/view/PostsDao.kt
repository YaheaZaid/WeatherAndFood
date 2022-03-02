package com.example.whattoeat2.view

import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.whattoeat2.model.Name
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPost(post:Name)

    @Delete
    fun deleteName(name:Name)

    @Query("DELETE FROM posts_table")
    fun deleteAll()

    @Query("select * from posts_table ORDER BY id ASC")
    fun getAll(): LiveData <List<Name>>

}