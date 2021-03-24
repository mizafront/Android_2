package ru.netology.android_v2.Posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.android_v2.db.AppDb
import ru.netology.android_v2.model.FeedModel
import ru.netology.android_v2.util.SingleLiveEvent
import java.io.IOException
import java.lang.Exception
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
       _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepositoryInMemoryImpl.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepositoryInMemoryImpl.GetAnyCallback{
                override fun onSuccess() {
                    loadPost()
                    edited.postValue(getEmptyPost())
                }

                override fun onError(e: Exception) {
                    error(e.toString())
                }
            })
        }

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
        repository.likeById(id, object : PostRepositoryInMemoryImpl.GetAnyCallback {
            override fun onSuccess() {
               data.value.let {
                   it?.posts?.map { post ->
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
                dataOnePost.postValue(_data.value?.posts?.filter { it.id == id }?.first())
            }

            override fun onError(e: Exception) {
                _data.postValue(data.value?.copy(error = true))
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepositoryInMemoryImpl.GetAnyCallback {
            override fun onSuccess() {
                try {
                    data.value?.posts
                            ?.filter { it.id != id }
                            .apply {
                                _data.postValue(
                                        this?.let {
                                            FeedModel(posts = it, it.isEmpty())
                                        })
                            }
                }catch (e : IOException) {
                    _data.postValue(FeedModel(error = true))
                }
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun clickPost(post: Post){
        dataOnePost.value = post
    }
}



