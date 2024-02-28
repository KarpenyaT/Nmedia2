package ru.netology.nmedia

import PostRepositoryInMemoryImpl
import PostViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.text.DecimalFormat
import kotlin.math.floor
import ru.netology.nmedia.dto.Post as Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater)//получить дерево иерархии из элементов
        setContentView(binding.root) //отобразить корень дерева
        val viewModel: PostViewModel  by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_favorited_24 else R.drawable.ic_favorite_border_24
                )
                likesText.text=formatNumber(post.likes)
                shareText.text=formatNumber(post.shareCount)
                viewedText.text=formatNumber(post.viewCount)

            }
        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }

//        with(binding){
//            author.text=post.author
//            published.text=post.published
//            content.text=post.content
//            likesText.text=formatNumber(post.likes)
//            shareText.text=formatNumber(post.shareCount)
//            viewedText.text=formatNumber(post.viewCount)
//
//            if (post.likedByMe){
//                likes.setImageResource((R.drawable.ic_favorited_24))
//            }
//
//            likes.setOnClickListener {
//                if (post.likedByMe) post.likes-- else post.likes++
//                post.likedByMe=!post.likedByMe
//                likes.setImageResource(if (post.likedByMe) R.drawable.ic_favorited_24 else R.drawable.ic_favorite_border_24)
//                likesText.text=formatNumber(post.likes)
//            }
//
//            share.setOnClickListener {
//                post.shareCount++
//                shareText.text=formatNumber(post.shareCount)
//            }
//        }

        }
        private fun formatNumber(oldNumber: Int): String {
            return if (oldNumber / 1_000_000 >= 1) {
                DecimalFormat("#.#").format(floor((oldNumber / 100_000).toDouble()) / 10) + "М"
            } else if (oldNumber / 1_000 >= 1) {
                DecimalFormat("#.#").format(floor((oldNumber / 100).toDouble()) / 10) + "К"
            } else {
                oldNumber.toString()
            }
        }
    }