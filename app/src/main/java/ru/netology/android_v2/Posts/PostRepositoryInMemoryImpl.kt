package ru.netology.android_v2.Posts

interface PostRepositoryInMemoryImpl {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}