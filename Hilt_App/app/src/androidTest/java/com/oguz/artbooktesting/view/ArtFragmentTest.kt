package com.oguz.artbooktesting.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){

        hiltRule.inject()

    }

    @Test
    fun testNavigationFromArtToArtDetails(){

        /**
         * navigation ın kendisini fake etmemiz gerekiyor. Bu da zor olacaktır bunun için
         * mock -> Complex tamamen oradaki sınıfn kendisini kopyaladıgımız ve ihtiyacımız oldugu akdar kullandıgımız bir yapı
         * buraya tıklayınca bu olacak gibi...
         */
        val navController = Mockito.mock(NavController::class.java)

            launchFragmentInHiltContainer<ArtFragment>(
                factory = fragmentFactory
            ){
                Navigation.setViewNavController(requireView(),navController)
                //TODO: Artık burada kendi oluşturdugum testi mock görümümde kullanabilirim.
            }

        //TODO: Click the Floating Action Bottom!
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())

        /**
         * AsserThat() yapar ki Mockito ya dogrulama yaptırıyoruz.
         * Dogrulama islemi ->  ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
         */
        Mockito.verify(navController).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
        )

    }
}