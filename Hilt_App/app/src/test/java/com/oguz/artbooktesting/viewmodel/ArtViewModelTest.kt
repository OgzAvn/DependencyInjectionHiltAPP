package com.oguz.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.oguz.artbooktesting.MainCoroutineRule
import com.oguz.artbooktesting.getOrAwaitValueTest
import com.oguz.artbooktesting.repo.FakeArtRepository
import com.oguz.artbooktesting.util.Status
import com.oguz.artbooktesting.viewModel.ArtViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecuteorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModelTest: ArtViewModel

    @Before
    fun setup(){

        //TODO:Test Doubles-> Kopyasını test ederiz kendisini değil reposityory içerisinde istemediğimiz bir sürü durum var
        viewModelTest = ArtViewModel(FakeArtRepository()) //TODO :Artık interface de tanımladığım fun ları deneyebileceğim

    }

    //TODO:makeArt ı test edelim.
    @Test
    fun `insert art without year returns error` (){

        viewModelTest.makeArt("Monalisa","davinci","") //TODO: Year vermeden Art oluşturduk.
        val value = viewModelTest.insertArtMessage.getOrAwaitValueTest()//TODO: LiveData dan çıktı böylece
        //TODO: viewModelTest.insertArtmessage -> LiveData döndürüyor bunu test işlemlerinde istemiyoruz çünlü LivaData Asynckron çalışıyor.
        assertThat(value.status).isEqualTo(Status.ERROR) // TODO :Error mu bakcağız yılı boş bıraktık.

    }

    @Test
    fun `insert art without name returns error` (){

        viewModelTest.makeArt("","davinvi","1900")
        val value = viewModelTest.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error` (){
        viewModelTest.makeArt("Monalisa","","1800")
        val value = viewModelTest.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

}