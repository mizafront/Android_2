package ru.netology.android_v2.Posts

import java.lang.Exception

interface PostRepositoryInMemoryImpl {
    fun getAll(): List<Post>
    fun likeById(id: Long, callback : GetAnyCallback)
    fun removeById(id: Long, callback : GetAnyCallback)
    fun save(post: Post, callback : GetAnyCallback)

    fun getAllAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface GetAnyCallback {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

}