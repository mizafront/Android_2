package ru.netology.android_v2.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.adapter.OnInteractionListener
import ru.netology.android_v2.adapter.PostsAdapter
import ru.netology.android_v2.databinding.ActivityMainBinding
import kotlin.collections.listOf as listOf1

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val postAdapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }
        })

        binding.recycler.adapter = postAdapter



        viewModel.dataList.observe(this) {
            postAdapter.submitList(it)
        }


    }


}