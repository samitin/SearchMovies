package ru.samitin.searchmovies.app

import android.app.Application
import androidx.room.Room
import ru.samitin.searchmovies.model.repository.room.AppDataBase
import ru.samitin.searchmovies.model.repository.room.HistoryDao

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance:App? = null
        private var db:AppDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao() : HistoryDao {
            if (db == null){
                synchronized(AppDataBase::class.java){
                    if (db == null){
                        if (appInstance == null)
                            throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(appInstance!!.applicationContext, AppDataBase::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db?.getHistoryDao()!!
        }


    }
}