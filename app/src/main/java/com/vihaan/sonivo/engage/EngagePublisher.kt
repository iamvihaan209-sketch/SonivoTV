package com.vihaan.sonivo.engage

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class EngagePublisher(private val context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun schedulePeriodicPublishing() {
        val workRequest = PeriodicWorkRequestBuilder<EngageWorker>(24, TimeUnit.HOURS)
            .setInputData(
                Data.Builder()
                    .putInt(EngageConstants.PUBLISH_TYPE_KEY, EngageConstants.PUBLISH_TYPE_ALL)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            EngageConstants.ENGAGE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun publishImmediately(publishType: Int = EngageConstants.PUBLISH_TYPE_ALL) {
        val workRequest = OneTimeWorkRequestBuilder<EngageWorker>()
            .setInputData(
                Data.Builder()
                    .putInt(EngageConstants.PUBLISH_TYPE_KEY, publishType)
                    .build()
            )
            .build()

        workManager.enqueueUniqueWork(
            "${EngageConstants.ENGAGE_WORK_NAME}_one_time_${System.currentTimeMillis()}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
