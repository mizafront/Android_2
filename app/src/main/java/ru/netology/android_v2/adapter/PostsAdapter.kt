package ru.netology.android_v2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.Posts.Util
import ru.netology.android_v2.R
import ru.netology.android_v2.databinding.PostCardBinding

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onClickPost(post: Post)
}

class PostsAdapter(private val OnInteractionListener: OnInteractionListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, OnInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val OnInteractionListener: OnInteractionListener
) :RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply {
            authorText.text = post.author
            contentText.text = post.content
            publisherText.text = post.published.toString()
            likesImage.isChecked = post.liked
            likesImage.text = Util.parseNumber(post.likesCount)

            likesImage.setOnClickListener {
                OnInteractionListener.onLike(post)
            }

            menuImage.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener {item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                OnInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                OnInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

           root.setOnClickListener{
                OnInteractionListener.onClickPost(post)
            }
        }


    }
}



class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Post, newItem: Post): Any = Unit
}