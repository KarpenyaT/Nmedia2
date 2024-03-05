package ru.netology.nmedia


import PostViewModel
import PostsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            ActivityMainBinding.inflate(layoutInflater)//получить дерево иерархии из элементов
        setContentView(binding.root) //отобразить корень дерева

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(
            { viewModel.likeById(it.id) },
            { viewModel.shareById(it.id) }
        )

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.recView.adapter = adapter
    }


}