package ru.netology.android_v2.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.adapter.OnInteractionListener
import ru.netology.android_v2.adapter.PostsAdapter
import ru.netology.android_v2.databinding.ActivityMainBinding


class PostActivity : AppCompatActivity() {

    private val newPostRequestCode = 1
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postAdapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, post.content)
                        .let {
                            Intent.createChooser(it, null)
                        }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this@PostActivity, "No any more app",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                val intent = Intent(this@PostActivity, CreatPostActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, post.content)
                startActivityForResult(intent, newPostRequestCode)
            }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        })

        binding.recycler.adapter = postAdapter

        viewModel.dataList.observe(this) {
            postAdapter.submitList(it)
        }

        binding.saveButton.setOnClickListener {
            val intent = Intent(this, CreatPostActivity::class.java)
            startActivityForResult(intent, newPostRequestCode)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            newPostRequestCode -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }

                data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    viewModel.changeContent(it)
                    viewModel.save()
                }
            }
        }
    }
}