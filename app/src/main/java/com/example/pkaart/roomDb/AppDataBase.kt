package com.example.pkaart.roomDb

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {


    companion object {

        private var database: AppDataBase? = null
        private const val DATABASE_NAME = "Pkaart"


        @Synchronized
        fun getInstance(context: Context): AppDataBase {


                if (database == null) {

                    database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    Log.d("DataBase","$database")

                }

            return database!!

        }

    }

    abstract fun productDao(): ProductDao
}