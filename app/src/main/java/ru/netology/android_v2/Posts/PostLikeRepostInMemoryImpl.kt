package ru.netology.android_v2.Posts

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostLikeRepostInMemoryImpl : PostRepositoryInMemoryImpl{

    var currentId = 1

    private var post = Post(
            0,
            "Рассия",
            "01 декабря 2020",
            "Это первый пост созданный 1 декабря 2020 года.\\n\\n",
            false,
            8,
            false,
            9,
            3,
        "https://www.youtube.com"
    )

    private var posts = listOf(
            Post(
                    currentId++,
                "First",
                "01 декабря 2020",
                "Это первый пост созданный 1 декабря 2020 года.",
                false,
                6,
                false,
                    7,
                111,
                "https://www.youtube.com"
    ),
            Post(
                    currentId++,
                "Второй пост.",
                "01 декабря 2020",
                "Это второй пост созданный 2 декабря 2020 года.",
                false,
                15,
                false,
                4444,
                111,
                "https://www.youtube.com"
            ),
            Post(
                    currentId++,
                "Третий.",
                "01 декабря 2020",
                "Это третий пост созданный 3 декабря 2020 года.",
                false,
                90,
                false,
                9,
                111,
                "https://www.youtube.com"
    ),
        Post(
                currentId++,
                "Четвертый.",
                "01 декабря 2020",
                "Это четвертый пост созданный 4 декабря 2020 года.",
                false,
                323,
                false,
                11111,
                111,
            "https://www.youtube.com"
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