package com.oguz.artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.oguz.artbooktesting.adapter.ArtRecyclerAdapter
import com.oguz.artbooktesting.adapter.ImageRecyclerAdapter
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.viewModel.ArtViewModel
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(

    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide : RequestManager //TODO : Glide ın kendi özelliği requestmanager olarak yazıyoruz.


) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            else ->  super.instantiate(classLoader, className)
        }

    }
}

/* TODO : "Eğer constructor aşamasında sınıfı(Fragment) belirli parametrelerle (parametre = constructor'da verilen veri) başlatmak istiyorsak
FragmentFactory kullanılması gerekiyor bunun aynısı ViewModel içerisinde de var. Nasıl ki ViewModel'ı belli bir parametrelerle başlatmak istediğimizde
ViewModelFactory kullanılması gerekiyor ise Fragment içerisinde aynısı geçerli.
Peki Atıl Hoca örnekte ne yaptı: Örneğin Glide'ı tüm fragmentlarda tek tek tanımlayabiliriz
ama işin içerisinde dependency injection olduğu için fragmentlar da tek tek tanımlamak yerine otomatik olarak tanımlansın istiyoruz.
Bunu yaparken de constructor injection yöntemi kullanıyoruz. Boş bir Fragment sınıfının yapısını incelersen de onCreateView ve onViewCreated gibi dahili
fonksiyonlar haricinde bir constructor'ı olmadığını görebilirsin. Ancak biz dependency injection ile glide'ı constructor içerisinde vereceğiz bu yüzden
bir constructor lazım. Bunu nasıl oluşturacağız dersen onu da işte FragmentFactory ile oluşturabiliyoruz. Yani tüm mesele bundan ibaret. Örneğin Ders 79
5:54'e bakarsan ArtDetailsFragment sınıfı otomatik olarak oluşurken constructor içerisine glide verildiğini görebilirsin"
 */
