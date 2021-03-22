package ru.netology.android_v2.Posts

import java.lang.Exception

interface PostRepositoryInMemoryImpl {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)

    fun getAllAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

}