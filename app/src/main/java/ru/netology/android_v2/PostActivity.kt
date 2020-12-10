package ru.netology.android_v2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
                authorText.text = post.author
                publisherText.text = post.published
                contentText.text = post.content
                likesText.text = Util.parseNumber(post.likesCount)
                repostText.text = Util.parseNumber(post.shareCount)
                watchText.text = Util.parseNumber(post.viewCount)
                if (post.liked) likesImage.setImageResource(R.drawable.ic_action_like_red_24)
                else likesImage.setImageResource(R.drawable.ic_action_like)
            }
        })

        binding.likesImage.setOnClickListener {
            viewModel.like()
        }

        binding.repostImage.setOnClickListener {
            viewModel.share()
        }



    }
}