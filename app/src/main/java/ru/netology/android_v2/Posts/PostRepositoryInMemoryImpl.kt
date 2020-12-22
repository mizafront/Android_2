package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData

interface PostRepositoryInMemoryImpl {
    fun get(): LiveData<Post>
    fun getAll(): LiveData<List<Post>>
    fun like()
    fun likeById(id: Int)
    fun share()
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)
}