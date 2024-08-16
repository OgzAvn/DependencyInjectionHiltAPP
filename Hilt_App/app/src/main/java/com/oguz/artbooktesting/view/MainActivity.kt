package com.oguz.artbooktesting.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.oguz.artbooktesting.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//TODO: Eklemen lazım hilt le nereden uygulamnın baslayacagını soylememız lazım.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //TODO: FragmentFactory imi buraya inject etmem gerekiyor. Kullanılacagını söylüyoruz.
    @Inject
    lateinit var artFragmentFactory: ArtFragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = artFragmentFactory
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) //TODO:klevyeyi ayarlıyor ekranı kapatmasın diye
        

    }
}