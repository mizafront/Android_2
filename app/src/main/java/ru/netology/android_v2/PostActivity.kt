package ru.netology.android_v2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.Posts.Util
import ru.netology.android_v2.databinding.ActivityMainBinding

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this, { post ->
            with(binding) {
                author.text = post.author
                publisher.text = post.published
                content.text = post.content
                text_likes.text = Util.parseNumber(post.likesCount)
                text_repost.text = Util.parseNumber(post.shareCount)
                text_watch.text = Util.parseNumber(post.viewCount)
                if (post.liked) likes.setImageResource(R.drawable.ic_action_like_red_24)
                else likes.setImageResource(R.drawable.ic_action_like)
            }
        })

        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.repost.setOnClickListener {
            viewModel.share()
        }



    }
}