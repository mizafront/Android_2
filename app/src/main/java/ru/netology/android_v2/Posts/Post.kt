package ru.netology.android_v2.Posts

data class Post(
        val id: Long,
        val author: String = "me",
        val authorAvatar: String = "tcs.jpg",
        val content: String = "Ура нас много!",
        val published: Long ,
        val liked: Boolean,
        val likesCount: Int = 0,
        val  attachment: Attachment? = null
)

fun getEmptyPost(): Post {
    return Post(
            0L,
            "Me",
            "tcs.jpg",
            "Now",
            0,
            false,
            0,

    )
}