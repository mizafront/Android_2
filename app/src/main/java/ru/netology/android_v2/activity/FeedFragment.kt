package ru.netology.android_v2.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.R
import ru.netology.android_v2.adapter.OnInteractionListener
import ru.netology.android_v2.adapter.PostsAdapter
import ru.netology.android_v2.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {



    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                bundleOf("contentEdit" to post.content))
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)

            }


            override fun onClickPost(post: Post) {
                viewModel.clickPost(post)
                findNavController().navigate(R.id.action_feedFragment_to_onePostFragment)
            }

        })

        binding.recycler.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })

        binding.saveButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }
}