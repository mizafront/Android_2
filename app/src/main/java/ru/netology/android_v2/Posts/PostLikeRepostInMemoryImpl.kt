package ru.netology.android_v2.Posts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostLikeRepostInMemoryImpl(
        context: Context
) : PostRepositoryInMemoryImpl{

    private companion object{
        const val POST_FILE = "posts.json"
    }

    var currentId = 1
    private val file = context.filesDir.resolve(POST_FILE)
    //private val preferences = context.getSharedPreferences(POST_FILE, Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val gson = Gson()
    private var posts: List<Post> = file.exists().let {exists ->
        if (exists) {
            gson.fromJson(file.readText(), type)
        }else {
            emptyList()
        }
    }
        set(value) {
            field =value
            sync()
        }

    private val dataPost = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = dataPost
    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                    liked = !it.liked,
                    likesCount = if (!it.liked) it.likesCount + 1 else it.likesCount - 1
            )
        }
        dataPost.value = posts
    }



    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCount = it.shareCount + 1)
        }
        dataPost.value = posts
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id != id }
        dataPost.value = posts
    }

    override fun save(post: Post) {

        if (post.id == 0) {
            posts = listOf(
                    post.copy(id = currentId++)
            ) + posts
            dataPost.value = posts
        }

        posts = posts.map {
            if (post.id != it.id) it else it.copy(content = post.content)
        }

        dataPost.value = posts
    }

    private fun sync() {
        file.writeText(gson.toJson(posts))

    }
}