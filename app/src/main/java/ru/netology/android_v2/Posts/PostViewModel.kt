package ru.netology.android_v2.Posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PostViewModel: ViewModel() {
    private val repository: PostRepositoryInMemoryImpl = PostLikeRepostInMemoryImpl()

    val dataList = repository.getAll()

    val edited = MutableLiveData(getEmptyPost())

    fun save(){
        edited.value?.let {
            repository.save(it)
        }
        edited.value = getEmptyPost()
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun like() = repository.like()

    fun likeById(id: Int) = repository.likeById(id)

    fun share() = repository.share()

    fun shareById(id: Int) = repository.shareById(id)

    fun removeById(id: Int) = repository.removeById(id)
}