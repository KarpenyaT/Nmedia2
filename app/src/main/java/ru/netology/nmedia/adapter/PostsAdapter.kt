import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.text.DecimalFormat
import kotlin.math.floor

interface OnInteractionListener{
    fun onLike(post:Post)
    fun onShare(post:Post)
    fun onRemove(post:Post)
    fun onEdit(post:Post)

}

class PostsAdapter(
    private val OnInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, OnInteractionListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val OnInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likes.setImageResource(
                if (post.likedByMe) R.drawable.ic_favorited_24 else R.drawable.ic_favorite_border_24
            )
            likes.setOnClickListener {
                OnInteractionListener.onLike(post)
            }
            likesText.text = formatNumber(post.likes)

            share.setOnClickListener {
                OnInteractionListener.onShare(post)
            }
            shareText.text = formatNumber(post.shareCount)
            viewedText.text = formatNumber(post.viewCount)

            menu.setOnClickListener {
                PopupMenu(it.context,it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId){
                            R.id.remove->{
                                OnInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit->{
                                OnInteractionListener.onEdit(post)
                                true
                            }
                            else->false
                        }
                    }
                }.show()
            }
        }
    }

    private fun formatNumber(oldNumber: Long): String {
        return if (oldNumber / 1_000_000 >= 1) {
            DecimalFormat("#.#").format(floor((oldNumber / 100_000).toDouble()) / 10) + "М"
        } else if (oldNumber / 1_000 >= 1) {
            DecimalFormat("#.#").format(floor((oldNumber / 100).toDouble()) / 10) + "К"
        } else {
            oldNumber.toString()
        }
    }

}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}