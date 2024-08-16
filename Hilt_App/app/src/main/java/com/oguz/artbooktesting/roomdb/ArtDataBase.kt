package com.oguz.artbooktesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oguz.artbooktesting.model.Art

@Database(entities = [Art::class], version = 1, exportSchema = false)
abstract class ArtDataBase : RoomDatabase(){

    abstract fun artDao() : ArtDao


}