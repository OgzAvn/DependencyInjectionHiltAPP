package com.oguz.artbooktesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.oguz.artbooktesting.getOrAwaitValue
import com.oguz.artbooktesting.model.Art
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/*TODO : ApplicationContext olduğu için androidTest içerisinde yapıyoruz.*/

@SmallTest //TODO: Unit testler
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    /*TODO @get:Rule  var mainCoroutineRule = MainCoroutineRule()
    TODO : bunu kullanmadık çünkü zaten emilatörde çalıştırıyoruz MainThread de çalışıyor */

    //TODO: Bütün herşeyi MainThread de çalıştıracağımız söylemek için gerekli.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    @Named("testDatabase") /*TODO:Gerçek database ile karışmasın diye isim verdik */
    lateinit var dataBase: ArtDataBase

    private lateinit var dao: ArtDao

    @Before
    fun setup(){


        //TODO: Database imizi oluşturacağız Dao yu oluşturmak için.
        //TODO : inMemoryDatabaseBuilder -> Geçiçi bir veri tabanı veriyi Ram de saklıyoruz ve işlem bitince siliniyor.
       /*
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDataBase::class.java
        ).allowMainThreadQueries().build()
            //TODO : allowMainThreadQueries -> MainThread de çalışacağımızı söylüyoruz.


        */

        /*TODO :Burada 2 adet func test ettik ama 100 tane de func test edebilirdik ve herseferinde inMemoryDatabaseBuilder
        TODO : oluşturmak biraz app için sorun olabilir o sebeple Hilt i Dependency Injection ı işin içine sokmamız lazım */

        hiltRule.inject()

        dao = dataBase.artDao()
    }

    @After
    fun teardown(){

        dataBase.close()
    }


    @Test
    fun insertArtTesting() = runBlocking{ // TODO : diğer thread leri engelliyor. Aynı anda Farklı Thread istemiyoruz

        val exampleArt = Art("Mona","Da vinci",1700,"www.test.com",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt) //TODO: ExampleArt barınıyor mu? diye bakıyoruz.

    }

    @Test
    fun deleteArtTesting() = runBlocking{


        val exampleArt = Art("Mona","Da vinci",1700,"www.test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)//Barındırmıyorsa silmiş demektir.

    }



}