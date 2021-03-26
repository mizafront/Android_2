package ru.netology.android_v2.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.Posts.Util
import ru.netology.android_v2.R
import ru.netology.android_v2.databinding.FragmentOnePostBinding


class OnePostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding = FragmentOnePostBinding.inflate(layoutInflater)

        viewModel.dataOnePost.observe(viewLifecycleOwner, { post ->
            binding.apply {
                authorTextOnePost.text = post.author
                contentTextOnePost.text = post.content
                publisherTextOnePost.text = post.published.toString()
                likesImageOnePost.isChecked = post.liked
                likesImageOnePost.text = Util.parseNumber(post.likesCount)

                likesImageOnePost.setOnClickListener {
                    viewModel.likeById(post.id)
                }

                menuImageOnePost.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener {
                            when (it.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(post.id)
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.action_onePostFragment_to_newPostFragment,
                                            bundleOf("contentEdit" to post.content))
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }


            }
        })

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_onePostFragment_to_feedFragment)
        }
            return binding.root
    }
}