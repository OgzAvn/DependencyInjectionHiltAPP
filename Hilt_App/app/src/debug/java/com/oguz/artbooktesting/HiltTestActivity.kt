package com.oguz.artbooktesting

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/*TODO : Hilt bu activvty i arayacak boş bir aktvite olarak kullanılacak
*   Bunu yaptıktan sonra normalde biz nasıl aktiviteleri ,servisleri,broadCast Recieverları ve ContentProviderları
*   manifest içerisinde tanımlanıyoruz aynı şekilde bunuda manifest içerisinde tanımlamamız gerekiyor.
*   {Kendi AndroidManifest ini açıp orada yapman gerekir.}*/
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity(){



}