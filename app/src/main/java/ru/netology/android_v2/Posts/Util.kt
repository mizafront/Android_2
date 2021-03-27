package ru.netology.android_v2.Posts

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.netology.android_v2.R
import java.math.BigDecimal

object Util {
    fun parseNumber(number: Int): String {

        val currentNumber = BigDecimal.valueOf(number.toDouble())

        return when (number) {
            in 0..999 -> number.toString()
            in 1000..9999 -> parseDecimal(currentNumber.divide(BigDecimal(1000.0))) + "K"
            in 10_000..999_999 -> (number / 1000).toString() + "K"
            else -> parseDecimal(currentNumber.divide(BigDecimal(1_000_000.0))) + "M"
        }
    }

    private fun parseDecimal(number: BigDecimal): String {

        val currentNumber = number.setScale(1, BigDecimal.ROUND_DOWN)

        val decimalPart = currentNumber.remainder(BigDecimal.ONE).multiply(BigDecimal(10))

        return when (decimalPart.compareTo(BigDecimal(0.0))) {
            0 -> currentNumber.setScale(0, BigDecimal.ROUND_DOWN).toString()
            else -> currentNumber.toString()
        }
    }

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun ImageView.loadingImg(urls: String, authorAvatar: String) {
        val url = "$urls${authorAvatar}"
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_loading_100dp)
                .error(R.drawable.ic_error_100dp)
                .timeout(10_000)
                .into(this)
    }
}