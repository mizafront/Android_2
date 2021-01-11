package ru.netology.android_v2.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import ru.netology.android_v2.Posts.Post
import ru.netology.android_v2.R
import ru.netology.android_v2.databinding.ActivityCreatPostBinding

class CreatPostActivity : AppCompatActivity() {

    companion object {
        const val POST_KEY = "post"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreatPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setContent(binding.creat)

        binding.creatContent.setOnClickListener{
            val text = binding.creat.text?.toString()
            if (text.isNullOrBlank()) {
                setResult(RESULT_CANCELED)
            }else {
                val content = binding.creat.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

    }
    private fun setContent(contentEditText: EditText) {
        contentEditText.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
    }
}




