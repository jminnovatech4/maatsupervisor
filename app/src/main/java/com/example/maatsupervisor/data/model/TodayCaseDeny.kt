package com.example.maatsupervisor.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TodayCaseDeny(
    val vehicleNo: String,
    val remark: String,
    val attemptedTime: String,
    val todayCount: Int
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val remainingMillis: Long
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val attempted = LocalDateTime.parse(attemptedTime, formatter)
            val expiry = attempted.plusHours(10)
            return Duration.between(LocalDateTime.now(), expiry).toMillis()
        }

    val isExpired: Boolean
        @RequiresApi(Build.VERSION_CODES.O)
        get() = remainingMillis <= 0
}
fun formatRemaining(ms: Long): String {
    if (ms <= 0) return "Expired"

    val totalSeconds = ms / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return "%02d:%02d:%02d left".format(hours, minutes, seconds)
}
