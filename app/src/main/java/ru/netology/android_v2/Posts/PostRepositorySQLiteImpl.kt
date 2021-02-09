package ru.netology.android_v2.Posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.netology.android_v2.dao.PostDao
import ru.netology.android_v2.entry.PostEntity

class PostRepositorySQLiteImpl(
        private val dao: PostDao
) : PostRepositoryInMemoryImpl {


    override fun getAll(): LiveData<List<Post>> = Transformations.map(dao.getAll()) {  list ->
        list.map { entity ->
            entity.toPost()
        }
    }


    override fun save(post: Post) {
        dao.save(PostEntity.fromPost(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)

    }

    override fun removeById(id: Long) {
        dao.removeById(id)

    }
}
