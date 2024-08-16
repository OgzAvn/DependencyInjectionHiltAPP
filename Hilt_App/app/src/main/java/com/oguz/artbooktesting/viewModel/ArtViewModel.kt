package com.oguz.artbooktesting.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguz.artbooktesting.model.Art
import com.oguz.artbooktesting.model.ImageResponse
import com.oguz.artbooktesting.repo.ArtRepositoryInterface
import com.oguz.artbooktesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Tek bir viewmodel içerisinde bütün fragmentlerı ele alacağız


@HiltViewModel
class ArtViewModel @Inject constructor(

    private val repository: ArtRepositoryInterface

) :ViewModel() {

    // TODO : Art Fragmentdaki işlemler için oluşturdum
    val artList = repository.getArt() //TODO: Bizim kaydedilen listemiz.

    //*******************************************************************************

    // TODO : Image API fragment da gelen görseli seçip ArtDetailsFragmenta yolluyorum bu işlemler için oluşturdum
    // TODO : Gelen görselleri kaydettiğimiz ve seçilen görselin URL sini kaydettiğimiz 2 tane LiveData oluşturacağız.
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>> //TODO: imageList -> private val images in degeri ne ise onu alacak.
        //TODO: Dışarıdan sadece imageList e ulaşıcağım ve bunu set edemeyeceğim sadece get yapıp degerini alabilirim.
        get() = images // TODO : getter -> Heryerden ulaşabilirim.

    //TODO:imageList images ın değeri ne ise onu alacak ben dışardan ulaşmaya çalıştığımda imageList e ulaşacağım
    //TODO :böylece dışardan bunu set edemeyeceğim sadece değerini alacağım

    private val selectedImage = MutableLiveData<String>() //TODO:Kullanıcını sectigi görsel in URL sini saklayacağız
    val selectedImageUrl : LiveData<String>
        get() = selectedImage



    //TODO:Art Details Fragment işlemler için oluşturdum verileri orada giriyoruz. -> insertArt,deleteArt
    //TODO: Bu eklenme durumunun ne olduğunu tutan bir mesaj değişkeni oluşturacağız. -> insertArtMsg

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg


   /*TODO : Resource sadece success error ve loading degerlerini alabiliyor ama ben öyle bir zaman isterim ki resource
   TODO:nötr olsun bunu yapmazsak bug lar ile karşılaşabiliriz uygulamamızda.
    */
    fun resetInsertArtMSg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    //Kullanıcı bir görsel seçtiğinde kullanılacak
    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art : Art) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt(art : Art) = viewModelScope.launch {
        repository.inserArt(art)
    }

    //TODO: UI kullanıcı year ı integer olarak girmişmi resmi seçmiş mi gibi save e basıp insert etmeden önce kontrol edeceğiz
    fun makeArt(name :String,artistName : String,year: String) {

        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name,artist,year",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        //TODO: Hiç hata yoksa ART ı mı oluşturabilirim.
        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("") //TODO : setSelectedImage i boşalttım öyle kalsın diye.
        insertArtMsg.postValue(Resource.success(art))
    }


    fun searchForImage(searchString : String){
        if (searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null) /*TODO: API istegini yapacagız Gelen istegi alıyoruz burada */
        viewModelScope.launch {

            val response = repository.searchImage(searchString) //TODO: Burda istek atılıyor
            images.value = response

        }
    }


}