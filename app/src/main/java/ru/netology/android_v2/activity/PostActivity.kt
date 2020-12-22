package ru.netology.android_v2.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.Posts.Util
import ru.netology.android_v2.R
import ru.netology.android_v2.adapter.OnInteractionListener
import ru.netology.android_v2.adapter.PostsAdapter
import ru.netology.android_v2.databinding.ActivityMainBinding


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

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.recycler.adapter = postAdapter

        viewModel.dataList.observe(this) {
            postAdapter.submitList(it)
            group.visibility = View.INVISIBLE
        }

        viewModel.edited.observe(this, { post ->
            if (post.id == 0) {
                return@observe
            }
            group.visibility = View.VISIBLE
            with(binding.editContent) {
                requestFocus()
                setText(post.content)
            }
            with(binding.saveEditContent) {
                requestFocus()
                setText(post.content)
            }

        })

        binding.deleteSaveButton.setOnClickListener {
            with(binding.editContent){
                setText("")
            }
            with(binding.saveEditContent){
                setText("")
                clearFocus()
                Util.hideKeyboard(this)
            }
            group.visibility = View.INVISIBLE
        }

        binding.saveButton.setOnClickListener {
            with(binding.editContent) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                            this@PostActivity,
                            context.getString(R.string.empty_text),
                            Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                Util.hideKeyboard(this)
            }
        }

    }

}