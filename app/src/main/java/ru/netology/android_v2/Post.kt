package ru.netology.android_v2

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val liked: Boolean = false,
    val likesCount: Int = 0,
)
