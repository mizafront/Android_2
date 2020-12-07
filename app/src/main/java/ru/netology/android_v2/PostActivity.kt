package ru.netology.android_v2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.android_v2.databinding.ActivityMainBinding

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var post = Post(
            0,
            "Нетология. Университет интернет-професий.",
            "21 мая в 18:36",
            "Привет, это навая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркентингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичка до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> https://netology.ru" ,
            false,
            10_999,
            false,
            0,
            44
        )

        with(binding) {
            author.text = post.author
            publisher.text = post.published
            content.text = post.content
            text_likes.text = Util.parseNumber(post.likesCount)
            text_repost.text = Util.parseNumber(post.shareCount)
            text_watch.text = Util.parseNumber(post.viewCount)
            if (post.liked) {
                likes.setImageResource(R.drawable.ic_action_like_24)
            }

            likes.setOnClickListener {

                post = post.copy(
                    liked = !post.liked,
                    likesCount = if (!post.liked) post.likesCount + 1 else post.likesCount - 1
                )

                likes.setImageResource(
                    if (post.liked) R.drawable.ic_action_like_24 else R.drawable.ic_action_like
                )
                text_likes.text = Util.parseNumber(post.likesCount)

            }

            repost.setOnClickListener {

                post = post.copy(shareCount = post.shareCount + 1)
                text_repost.text = Util.parseNumber(post.shareCount)

            }

            root.setOnClickListener{
                val toast = Toast.makeText(applicationContext, "root element click", Toast.LENGTH_SHORT)
                toast.show()
            }

            author.setOnClickListener{
                val toast = Toast.makeText(applicationContext, "author image view click", Toast.LENGTH_SHORT)
                toast.show()
            }

        }


    }
}


