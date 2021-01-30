package ru.netology.android_v2.Posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepositoryInMemoryImpl = PostLikeRepostInMemoryImpl(application)

    val dataList = repository.getAll()
    val dataOnePost = MutableLiveData(getEmptyPost())
    val edited = MutableLiveData(getEmptyPost())

    fun save(){
        edited.value?.let {
            repository.save(it)
            setPostData(it)
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

    fun likeById(id: Int) {
        repository.likeById(id)
        dataOnePost.value = repository.getById(id)
    }

    fun shareById(id: Int) = repository.shareById(id)

    fun removeById(id: Int) = repository.removeById(id)

    fun clickPost(post: Post){
        dataOnePost.value = post
    }

    fun setPostData(post: Post) {
        dataOnePost.value = post
    }
}