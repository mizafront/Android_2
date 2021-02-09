package ru.netology.android_v2.Posts

data class Post(
        val id: Long,
        val author: String = "me",
        val published: String = "now",
        val content: String,
        val liked: Boolean = false,
        val likesCount: Int = 0,
)

fun getEmptyPost(): Post {
    return Post(
            0L,
            "Me",
            "Now",
            "",
            false,
            0,

    )
}