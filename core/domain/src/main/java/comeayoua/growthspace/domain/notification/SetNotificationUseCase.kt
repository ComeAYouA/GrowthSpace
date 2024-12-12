package comeayoua.growthspace.domain.notification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import comeayoua.growthspace.core.domain.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.UUID
import javax.inject.Inject

class SetNotificationUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(habitId: UUID, time: LocalDateTime) {
        val id = habitId.hashCode()
        val zoneId = TimeZone.currentSystemDefault()
        val notificationTime = time.toInstant(zoneId).toEpochMilliseconds()
        val intent = Intent(
            context,
            ReminderReceiver::class.java
        ).apply {
            putExtra("habitId", id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            604_800_000L,
            pendingIntent
        )

        Log.d("myTag", "alarm: $notificationTime, $habitId")
    }
}

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("myTag", "recive")
        val habitId = intent.getIntExtra("habitId", -1)
        if (habitId != -1) {
            showNotification(context, habitId)
        }
    }

    private fun showNotification(context: Context, habitId: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создайте уведомление
        val notification = NotificationCompat.Builder(context, "HabitChannel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Напоминание о привычке")
            .setContentText("Пора выполнить привычку!")
            .build()

        notificationManager.notify(habitId, notification)
    }
}