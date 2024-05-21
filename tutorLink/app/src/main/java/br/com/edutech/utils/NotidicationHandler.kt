package br.com.edutech.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import br.com.edutech.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    // SIMPLE NOTIFICATION
    fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Olha que legal!")
            .setContentText("Novo Match!")
            .setSmallIcon(androidx.core.R.drawable.ic_call_answer_video_low)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()  // finalizes the creation

        notificationManager.notify(Random.nextInt(), notification)
    }
}