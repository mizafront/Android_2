package ru.netology.android_v2.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit_post.*
import ru.netology.android_v2.R
import ru.netology.android_v2.databinding.ActivityCreatPostBinding
import ru.netology.android_v2.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.editContent.setOnClickListener{
            binding.edit.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
            finish()
        }
    }

}