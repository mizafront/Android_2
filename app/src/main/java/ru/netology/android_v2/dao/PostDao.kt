package ru.netology.android_v2.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.android_v2.entry.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun save(post: PostEntity)

    @Query("""
        UPDATE PostEntity SET
               likesCount = likesCount + CASE WHEN liked THEN -1 ELSE 1 END,
               liked = CASE WHEN liked THEN 0 ELSE 1 END
           WHERE id = :id;
    """)
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)
}