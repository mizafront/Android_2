package ru.netology.android_v2

data class Post (
        val id: Long,
        val author: String = "me",
        val authorAvatar: String = "tcs.jpg",
        val content: String = "Ура нас много!",
        val published: Long = 0,
        val liked: Boolean,
        val likesCount: Int = 0
)