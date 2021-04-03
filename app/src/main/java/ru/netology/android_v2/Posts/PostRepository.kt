package ru.netology.android_v2.Posts

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import ru.netology.android_v2.api.PostsApi
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class PostRepository : PostRepositoryInMemoryImpl {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }


    override fun getAll(): List<Post> {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/posts")
            .build()

        return client.newCall(request)
            .execute()
            .let { it.body?.string() ?: throw RuntimeException("body is null") }
            .let {
                gson.fromJson(it,typeToken.type)
            }
    }

    override fun getAllAsync(callback: PostRepositoryInMemoryImpl.GetAllCallback) {
       PostsApi.retrofitService.getAll()
           .enqueue(object : Callback<List<Post>> {
               override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                   if (!response.isSuccessful) {
                       callback.onError(RuntimeException(response.message()))
                       return
                   }
                   callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
               }

               override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                  callback.onError(RuntimeException(t))
               }

           })
    }

    override fun likeById(id: Long, callback: PostRepositoryInMemoryImpl.GetAnyCallback) {
        PostsApi.retrofitService
            .likeById(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                    callback.onSuccess()
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })
    }

    override fun removeById(id: Long, callback: PostRepositoryInMemoryImpl.GetAnyCallback) {
        PostsApi.retrofitService
            .removeById(id)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                    callback.onSuccess()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }


            })
    }

    override fun save(post: Post, callback: PostRepositoryInMemoryImpl.GetAnyCallback) {
        PostsApi.retrofitService
            .save(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()))
                        return
                    }
                    callback.onSuccess()
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })
    }


}

