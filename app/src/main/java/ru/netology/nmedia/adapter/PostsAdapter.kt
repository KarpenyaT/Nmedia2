import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.text.DecimalFormat
import kotlin.math.floor

typealias onLikeShareListener = (Post) -> Unit

class PostsAdapter(
    private val onLike: onLikeShareListener,
    private val onShare: onLikeShareListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onLike, onShare)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLike: onLikeShareListener,
    private val onShare: onLikeShareListener
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
                onLike(post)
            }
            likesText.text = formatNumber(post.likes)
            share.setOnClickListener {
                onShare(post)
            }
            shareText.text = formatNumber(post.shareCount)
            viewedText.text = formatNumber(post.viewCount)
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