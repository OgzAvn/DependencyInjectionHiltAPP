package com.oguz.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.oguz.artbooktesting.roomdb.ArtDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,ArtDataBase::class.java)
            .allowMainThreadQueries()
            .build()


}