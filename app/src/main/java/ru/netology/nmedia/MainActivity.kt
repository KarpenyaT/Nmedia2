package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val post= Post(
            id= 1,
            author= "Нетология. Университет интернет-профессий будущего",
            published= "21 мая в 18:36",
            content= "Привет, это новая Нетологиxdrshdfbndhdsvc dvcsv vacvxzsaecя! Когда-то Нетология " +
                    "начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, " +
                    "разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: " +
                    "от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы " +
                    "верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться " +
                    "выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb",
            likes=999,
            shareCount = 199999,
            viewCount = 0
        )
        with(binding){
            author.text=post.author
            published.text=post.published
            content.text=post.content
            likesText.text=formatNumber(post.likes)
            shareText.text=formatNumber(post.shareCount)
            viewedText.text=formatNumber(post.viewCount)

            if (post.likedByMe){
                likes.setImageResource((R.drawable.ic_favorited_24))
            }

            likes.setOnClickListener {
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe=!post.likedByMe
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_favorited_24 else R.drawable.ic_favorite_border_24)
                likesText.text=formatNumber(post.likes)
            }

            share.setOnClickListener {
                post.shareCount++
                shareText.text=formatNumber(post.shareCount)
            }
        }

    }
    private fun formatNumber(oldNumber:Int):String{
        return if(oldNumber/1_000_000>=1){
            DecimalFormat("#.#").format(floor((oldNumber/100_000).toDouble())/10) + "М"
        }else if(oldNumber/1_000>=1){
            DecimalFormat("#.#").format(floor((oldNumber/100).toDouble())/10) + "К"
        }else{
            oldNumber.toString()
        }
    }
}