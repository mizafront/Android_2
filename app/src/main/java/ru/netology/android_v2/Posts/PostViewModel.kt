package ru.netology.android_v2.Posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.android_v2.db.AppDb


private val empty = Post(
        id = 0L,
        content = "",
        author = "",
        liked = false,
        likesCount = 0,
        published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepositoryInMemoryImpl = PostRepositorySQLiteImpl(
            AppDb.getInstance(application).postDao()
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    val dataOnePost = MutableLiveData(getEmptyPost())

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun clickPost(post: Post){
        dataOnePost.value = post
    }
}