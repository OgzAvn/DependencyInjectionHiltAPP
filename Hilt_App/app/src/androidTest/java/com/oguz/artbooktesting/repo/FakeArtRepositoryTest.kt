package com.oguz.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.util.Resource

//Bütün bunaları uygulacaız biz burda kendimize has bir liste oluşturacağız fake olacak network call filan olmayacak

class FakeArtRepositoryTest : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)


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

        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}