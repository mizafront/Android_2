package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostLikeRepostInMemoryImpl : PostRepositoryInMemoryImpl{
    private var post = Post(
            0,
            "Нетология. Университет интернет-професий.",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркентингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичка до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> https://netology.ru",
            false,
            10_299_999,
            false,
            0,
            44
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(liked = !post.liked, likesCount = if (!post.liked) post.likesCount + 1 else post.likesCount - 1)
        data.value = post
    }

    override fun share() {
        post = post.copy(shareCount = post.shareCount + 1)
        data.value = post
    }
}