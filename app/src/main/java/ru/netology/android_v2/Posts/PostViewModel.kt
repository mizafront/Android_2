package ru.netology.android_v2.Posts

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepositoryInMemoryImpl = PostLikeRepostInMemoryImpl()
    val data = repository.get()

    fun like() = repository.like()

    fun share() = repository.share()
}