package ru.netology.android_v2.Posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.android_v2.db.AppDb
import ru.netology.android_v2.model.FeedModel
import ru.netology.android_v2.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread


private val empty = Post(
        id = 0L,
        content = "",
        author = "",
        liked = false,
        likesCount = 0,
        published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepositoryInMemoryImpl = PostRepository()
    val edited = MutableLiveData(empty)
    val dataOnePost = MutableLiveData(getEmptyPost())
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPost()
    }

    fun loadPost() {
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            }catch (e: IOException){
                FeedModel(error = true)
            }.also { _data::postValue }
        }
    }

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
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

    fun likeById(id: Long) {
        thread {
            try {
                repository.likeById(id)
                data.value?.let {
                    it.posts.map {post ->
                        if (post.id == id) {
                            post.copy(
                                id = post.id,
                                liked = !post.liked,
                                likesCount = post.likesCount + if (post.liked) -1 else 1
                            )
                        }else {
                            post
                        }
                    }
                }.also {
                    _data.postValue(it?.let { posts ->
                        FeedModel(
                            posts = posts,
                            empty = posts.isEmpty()
                        )
                    })
                }
                dataOnePost.postValue(data.value?.posts?.filter { it.id == id }?.first())
            }catch (e: IOException){
                _data.postValue(data.value?.copy(error = true))
            }
        }
    }

    fun removeById(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            }catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }

    fun clickPost(post: Post){
        dataOnePost.value = post
    }
}