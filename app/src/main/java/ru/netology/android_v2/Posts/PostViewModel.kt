package ru.netology.android_v2.Posts

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepositoryInMemoryImpl = PostLikeRepostInMemoryImpl()

    val dataList = repository.getAll()

    fun like() = repository.like()

    fun likeById(id: Int) = repository.likeById(id)

    fun share() = repository.share()

    fun shareById(id: Int) = repository.shareById(id)
}