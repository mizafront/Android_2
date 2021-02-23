package ru.netology.android_v2.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.android_v2.R
import java.lang.Exception
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()
    data class Failure(val e: Throwable)

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }


    override fun onMessageReceived(message: RemoteMessage) {

        message.data[action]?.let {
            try {
                when (Action.valueOf(it)) {
                    Action.NewPost -> handleLike(gson.fromJson(message.data[content], NewPost::class.java))
                }
            }catch (e: Exception){
                Failure(e)
            }

        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }

    private fun handleLike(content: NewPost) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_play_icons)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(content.textContent))
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000_000), notification)
    }
}

enum class Action {
    NewPost,
}

data class NewPost(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
    val textContent: String,
)