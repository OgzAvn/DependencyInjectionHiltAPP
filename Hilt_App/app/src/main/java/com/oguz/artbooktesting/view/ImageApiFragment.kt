package com.oguz.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.adapter.ImageRecyclerAdapter
import com.oguz.artbooktesting.databinding.FragmentImageApiBinding
import com.oguz.artbooktesting.util.Status
import com.oguz.artbooktesting.viewModel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(

     val imageRecyclerAdapter: ImageRecyclerAdapter

): Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        subscribeToObservers()


        //TODO : Adapter da listener yaparak aldığımız url
        imageRecyclerAdapter.setOnItemClickListener {url ->
            findNavController().popBackStack()
            viewModel.setSelectedImage(url)
        }


        var job : Job? = null
        //TODO: Kullanıcı hiç bir şey yapmadan çağırılması lazım delay vererek zaman alarak yapmasını istiyoruz.
        //TODO: Edittext e herbir şey yazılınca bu çağırılacak Coroutine kulllanıcam delay ve kitlenmememsi için
        binding.searchText.addTextChangedListener {
            job?.cancel() //TODO: Önceden bir job varsa onu iptal edeceğim. Her bir text değiştirince coroutine oluşturacağım.
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {searchText->
                    if (searchText.toString().isNotEmpty()){
                        viewModel.searchForImage(searchText.toString())
                    }
                }
            }
        }

    }

    private fun subscribeToObservers(){

        viewModel.imageList.observe(viewLifecycleOwner, Observer {

            when(it.status){
                //TODO: hits -> Kaç tane imageresult geldi ise bize liste halinde verir.
                Status.SUCCESS->{
                    val urls = it.data?.hits?.map {  imageResult ->

                        imageResult.previewURL //TODO: Sadece buna ihtiyacım olduğu için aldım model den ihtiyacım olan url yi alabilirim.

                    }

                    imageRecyclerAdapter.imageList = urls ?: listOf() //TODO: Yoksa direk boş liste verdik

                    fragmentBinding?.progressBar?.visibility = View.GONE

                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING->{
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })

    }
}