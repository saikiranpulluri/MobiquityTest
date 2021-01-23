package com.example.mobiquitytest.work

import android.content.Context
import androidx.work.*
import com.example.mobiquitytest.database.getDatabase
import com.example.mobiquitytest.ui.home.repository.DashboardRepository
import retrofit2.HttpException
import timber.log.Timber

class GetCurrentWeatherWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        fun send(
            context: Context,
            pincode: String
        ) {
            val req = OneTimeWorkRequestBuilder<GetCurrentWeatherWorker>()
                .setInputData(
                    workDataOf(
                        Pair("pincode", pincode)
                    )
                ).build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(pincode, ExistingWorkPolicy.KEEP, req)
        }
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = DashboardRepository(database)
        try {
            val pincode: String? = inputData.getString("pincode")
            repository.getCurrentWeatherInLoc(pincode)
            Timber.d("WorkManager: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}