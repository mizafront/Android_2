package ru.netology.android_v2.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.android_v2.Posts.PostViewModel
import ru.netology.android_v2.Posts.Util
import ru.netology.android_v2.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        setContent(binding.creat)
        binding.creat.requestFocus()
        binding.creatContent.setOnClickListener{
            if (!binding.creat.text.isNullOrBlank()) {
                val content = binding.creat.text.toString()
                viewModel.changeContent(content)
                viewModel.save()
            }
            Util.hideKeyboard(binding.root)
            findNavController().navigateUp()
        }

        return binding.root
    }
    private fun setContent(contentEditText: EditText) {
        val content = arguments?.getString("contentEdit")
        contentEditText.setText(content)

    }
}




