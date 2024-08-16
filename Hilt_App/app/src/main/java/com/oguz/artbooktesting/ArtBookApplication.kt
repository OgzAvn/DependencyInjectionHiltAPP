package com.oguz.artbooktesting

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

//Bİr sınıf oluşturmamız gerekiyor Hilt i bu app de kullanacağımız söylememiz için.

@HiltAndroidApp //Manifest e eklemen gerekiyor.
class ArtBookApplication : Application()