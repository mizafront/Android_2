package ru.netology.android_v2.entry

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.android_v2.Posts.Post

@Entity
data class PostEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val author: String = "me",
        val published: Long = 0,
        val authorAvatar: String,
        val content: String,
        val liked: Boolean,
        val likesCount: Int = 0,
){
        fun toPost(): Post = Post(id, author, authorAvatar, content, published,liked, likesCount)

        companion object {
                fun fromPost(post: Post): PostEntity =
                        PostEntity(post.id, post.author, post.published, post.authorAvatar,post.content, post.liked, post.likesCount)
        }
}