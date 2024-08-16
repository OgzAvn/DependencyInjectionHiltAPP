package com.oguz.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.oguz.artbooktesting.api.RetrofitApi
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.roomdb.ArtDao
import com.oguz.artbooktesting.util.Resource
import javax.inject.Inject

/* TODO : InserArt,DeleteArt, live dataları getir, image ları ara gibi 4 tane işlemimiz var zaten onları yazacaüım */
/* TODO : ama bunları yapmadan once arayuz yazayım onları burada uygulayayım nedeni sadece test işlemi yapacağımız için */
class ArtRepository
@Inject constructor(
    /* TODO: AppModul de yazdıgım Dao ve Api i buraya kullanmak için inject constructor etmem gerek. */
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi

) : ArtRepositoryInterface{

    /* TODO : Hilt ile birlikte artık bu functionları her yerde inject yapabileceğim */
    override suspend fun inserArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }


    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {

            val response = retrofitApi.imageSearch(imageString) /*TODO:API_KEY vermemize gerek yok */

            if (response.isSuccessful){
                /*TODO: Bu ifade, Retrofit API çağrısının döndürdüğü Response nesnesinin gövdesini alır.  */
                /* TODO : Genellikle bu, API'den dönen JSON verisinin bir model nesnesine dönüştürülmüş hali olan ImageResponse türündedir. */
                /* TODO : body() metodu, yanıtın gövdesini (yani asıl veriyi) döndürür. Eğer yanıt başarılı ise ve gövde varsa,  */
                /* TODO : bu metod ImageResponse nesnesini döndürecektir. Eğer yanıtın gövdesi yoksa (null ise), bu metod null döner. */
                response.body()?.let {imageResponse->
                    return@let Resource.success(imageResponse) /* TODO:  Bu durumda, Resource.success(it) değerini döndürür ve fonksiyonun geri kalanında başka bir işlem yapılmaz.*/
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("No data!",null)
        }
    }

}

/* TODO : Artık bu repo yu yazdım ve test etmek istersem interface ini kullanabilirim. */