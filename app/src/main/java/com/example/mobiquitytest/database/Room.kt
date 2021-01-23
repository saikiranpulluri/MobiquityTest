package com.example.mobiquitytest.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDao {
    @Query("select * from city")
    fun getAllCitiesLive(): LiveData<List<City>>

    @Query("select * from city")
    suspend fun getAllCities(): List<City>

    @Query("select * from city where pinCode=:pin")
    fun getCity(pin: String): City

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(aboutModels: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(about: City)

    @Query("DELETE FROM city")
    suspend fun deleteAll()
}

@Database(entities = [City::class], version = 1)
abstract class MobiquityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}

private lateinit var INSTANCE: MobiquityDatabase
fun getDatabase(context: Context): MobiquityDatabase {
    synchronized(MobiquityDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MobiquityDatabase::class.java,
                "mobiquity.db"
            ).build()
        }
    }
    return INSTANCE
}