package com.oguz.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.util.Resource


//Başka bir fake bir repo oluşturduğum zaman test işlemleri için bu interface i kullanabilirim
//Retrofit için daha çok bunu yapıyorum istek atıp data aldığım için
//SAnki cevap almış gibi bir durum yaratıytoruz ve test işlemlerini bunun üzerinden yapıyoruz.
interface ArtRepositoryInterface {

    suspend fun inserArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    //Bu da retrofit ondan fake yapıyoruz test için
    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}