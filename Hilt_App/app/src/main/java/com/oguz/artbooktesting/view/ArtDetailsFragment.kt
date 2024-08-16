package com.oguz.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.databinding.FragmentArtDetailsBinding
import com.oguz.artbooktesting.util.Status
import com.oguz.artbooktesting.viewModel.ArtViewModel
import java.util.Objects
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(

    val glide : RequestManager

): Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    private var _binding : FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        _binding = binding

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]



        binding.ArtimageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){ //Geri tuşuna basınca ne olacağını söyleyebiliriz.
            override fun handleOnBackPressed() {
                findNavController().popBackStack() //Bir önceki stack de ne varsa oraya git ve bunu kapat manasına geliyor.
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(callback) //Oluşturduğumuz callBack i ekliyourz.

        binding.saveButton.setOnClickListener{
            viewModel.makeArt(binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())
        }
        //TODO:makeArt -> Hem insertArt yapıyor hem kontrol ediyor girdileri

        subscribeToObservers()

    }

    private fun subscribeToObservers(){ //Viewmodel da gözlemliyoruz.

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url->
            _binding?.let {
                glide.load(url).into(it.ArtimageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMSg() //TODO: Neden kullandık?
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING->{

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}