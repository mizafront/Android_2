package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData

interface PostRepositoryInMemoryImpl {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}