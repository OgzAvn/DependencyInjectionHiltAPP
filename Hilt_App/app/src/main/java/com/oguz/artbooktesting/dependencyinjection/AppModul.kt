package com.oguz.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.api.RetrofitApi
import com.oguz.artbooktesting.repo.ArtRepository
import com.oguz.artbooktesting.repo.ArtRepositoryInterface
import com.oguz.artbooktesting.roomdb.ArtDao
import com.oguz.artbooktesting.roomdb.ArtDataBase
import com.oguz.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Hangi Component olduğunu seçebiliyoruz.
// SingletonComponent -> App çalıştığı sürece yaşam döngüsüne dahil olucak ve room u api ı heryerden inject edebileceğiz.

/*
Application	   SingletonComponent	        @Singleton
Activity	   ActivityRetainedComponent	@ActivityRetainedScoped
ViewModel	   ViewModelComponent	        @ViewModelScoped
Activity	   ActivityComponent	        @ActivityScoped
Fragment	   FragmentComponent	        @FragmentScoped
View	       ViewComponent	            @ViewScoped
View           annotated with               @WithFragmentBindings	ViewWithFragmentComponent	@ViewScoped
Service	       ServiceComponent	            @ServiceScoped
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModul {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
         Room.databaseBuilder(
            context, ArtDataBase::class.java, "ArtBookDb"
        ).build()



    @Singleton
    @Provides
    fun injectDao(dataBase: ArtDataBase) = dataBase.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitApi{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)

    }


    //TODO: Repository imizi kullanmak için burda bir Repo oluşturalım
    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao,api : RetrofitApi) = ArtRepository(dao,api) as ArtRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )


    //TODO:Bu modül sınıfnı artık istediğim her yerde çağırabilirim
    //TODO:Bu modül sınıfının yardımıyla beraber repository lerimi yazabilirim onları alıp viewmodel a bağlayabilirim

}