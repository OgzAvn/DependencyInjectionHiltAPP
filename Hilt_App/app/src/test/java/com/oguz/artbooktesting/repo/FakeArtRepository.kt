package com.oguz.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.util.Resource

//TODO: Bütün bunaları uygulacaız biz burda kendimize has bir liste oluşturacağız fake olacak network call filan olmayacak

class FakeArtRepository : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>() //TODO: Herhangi bir liste yaptım
    private val artsLiveData = MutableLiveData<List<Art>>(arts)
    //TODO:Buda degistirebilir liste içinde Art değişkenleri
    // olan boş bir liste alıyor şuan


    override suspend fun inserArt(art: Art) {

        arts.add(art)
        refreshData()

    }

    override suspend fun deleteArt(art: Art) {

        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {

        return artsLiveData

    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {

        //TODO: Bir tane resource döndürelim sanki basarili olmus gibi boş bir liste verdik
        // bir geri dönüş olsun yeterki
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    //TODO: Tabi add ve remove kısmında arts ı ekledik filan ama
    // artslivedata ya da ekleniyor mu emin olmamız gerekiyor -> refreshData()

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }


}