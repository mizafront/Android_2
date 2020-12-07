package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData

interface PostRepositoryInMemoryImpl {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}