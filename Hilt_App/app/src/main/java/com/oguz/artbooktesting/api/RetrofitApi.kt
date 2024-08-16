package com.oguz.artbooktesting.api



import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/")
    suspend fun imageSearch(

        @Query(value = "q") searchQuery : String, // yaptığımız istekte bu parametreleri istiyorsa yollamalıyız, istemiyorsa gerek yok
        @Query(value = "key") apiKey : String = API_KEY //Retrofit Query yapmamız gerekiyor.

    ) : Response<ImageResponse>
    //When we use Coroutines or RxJava in the project
    // to provide asynchronous execution , we don't need enqueue callback. We could just use Response.


    //Servis i Dependency Injection kullanarak yazacağız.
}