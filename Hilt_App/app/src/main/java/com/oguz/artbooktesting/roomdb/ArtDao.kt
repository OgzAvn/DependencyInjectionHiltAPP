package com.oguz.artbooktesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oguz.artbooktesting.model.Art

//Arayüzümüz
@Dao
interface ArtDao {

    //TODO :Suspend func -> Coroutine ilerde çalıştırdığımız asynkron olarak çalışan istediğimiz zaman durdurup çalıştırdığımız fonksyonlardır.
    // Kullanma amacımız -> Sonuçta burada veri kaydedip alıyoruz bunların hepsi yer kaplıyor ve bu işlemleri mainthread de yapmak istemiyoruz

    @Insert(onConflict = OnConflictStrategy.REPLACE)//TODO:Id ler kesişirse ne yapacak onconflict Replace->Yerine yazar
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)


    //TODO: Live data olarak geri alacağız gerekli viewmodel da kullandıktan sonra gözlemleyip değişimleri alabiliriz
    // Suspend yazma livedata zaten asynkorn çalışır gerek yok.
    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>


}