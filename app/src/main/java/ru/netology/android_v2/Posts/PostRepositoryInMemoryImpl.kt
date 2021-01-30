package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData

interface PostRepositoryInMemoryImpl {
    fun getAll(): LiveData<List<Post>>
    fun getById(id: Int): Post?
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)
}