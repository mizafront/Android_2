package ru.netology.android_v2.Posts

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostLikeRepostInMemoryImpl : PostRepositoryInMemoryImpl{

    var currentId = 1

    private var post = Post(
            0,
            "Первый пост.",
            "01 декабря 2020",
            "Это первый пост созданный 1 декабря 2020 года.\\n\\n",
            false,
            1,
            false,
            0,
            3
    )

    private var posts = listOf(
            Post(
                    currentId++,
                "Первый пост.",
                "01 декабря 2020",
                "Это первый пост созданный 1 декабря 2020 года.",
                false,
                1,
                false,
                0,
                111
    ),
            Post(
                    currentId++,
                "Второй пост.",
                "01 декабря 2020",
                "Это второй пост созданный 2 декабря 2020 года.",
                false,
                2,
                false,
                0,
                111
            ),
            Post(
                    currentId++,
                "Третий.",
                "01 декабря 2020",
                "Это третий пост созданный 3 декабря 2020 года.",
                false,
                3,
                false,
                0,
                111
    ),
        Post(
                currentId++,
                "Четвертый.",
                "01 декабря 2020",
                "Это четвертый пост созданный 4 декабря 2020 года.",
                false,
                4,
                false,
                0,
                111
        )
    )


    private val data = MutableLiveData(post)
    private val dataPost = MutableLiveData(posts)

    override fun get(): LiveData<Post> = data
    override fun getAll(): LiveData<List<Post>> = dataPost

    override fun like() {
        post = post.copy(
                liked = !post.liked,
                likesCount = if (!post.liked) post.likesCount + 1 else post.likesCount - 1
        )
    }

    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                    liked = !it.liked,
                    likesCount = if (!it.liked) it.likesCount + 1 else it.likesCount - 1
            )
        }
        dataPost.value = posts

    }

    override fun share() {
        post = post.copy(shareCount = post.shareCount + 1)
        data.value = post
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
}