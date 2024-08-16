package com.oguz.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.adapter.ImageRecyclerAdapter
import com.oguz.artbooktesting.launchFragmentInHiltContainer
import com.oguz.artbooktesting.repo.FakeArtRepositoryTest
import com.oguz.artbooktesting.viewModel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.google.common.truth.Truth.assertThat
import com.oguz.artbooktesting.getOrAwaitValue


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        //TODO:navController -> Seçim yapıldıgında bir onceki fragment a dönmesi lazım onu kontrol edeceğiz
        val navController = Mockito.mock(NavController::class.java)

        //TODO:selectedImageUrl -> En azından bir adet de olsa bir data vermemiz lazım sonucta görselin kendisini degil url sini veriyoruz ne istersen yaz!
        val selectedImageUrl = "oguzavanoglu.com"

        /**Kullanıcı bunu sececek ve ArtDetail se geri gidecek onu verify edeceğim ve bu seçilen "oguzavanoglu.com" url viewmodel
         * içerisinde bizim selectedImageUrl liveData sına gitti mi onu kontrol edeceğiz.
         */

        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ){

            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            imageRecyclerAdapter.imageList = listOf(selectedImageUrl)

        }

        Espresso.onView(withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageRecyclerView>(
                0, ViewActions.click()
            )
        )

        Mockito.verify(navController).popBackStack()

        //TODO: bu seçilen "oguzavanoglu.com" url viewmodel
        //         * içerisinde bizim selectedImageUrl liveData sına gitti mi onu kontrol edeceğiz.
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)

    }

}