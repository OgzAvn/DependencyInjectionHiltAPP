package com.oguz.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.adapter.ArtRecyclerAdapter
import com.oguz.artbooktesting.databinding.FragmentArtsBinding
import com.oguz.artbooktesting.viewModel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(

    val artRecyclerAdapter: ArtRecyclerAdapter //TODO: Inject etmen için Factory de initiate etmen gerek.


): Fragment(R.layout.fragment_arts) {


    private var _binding: FragmentArtsBinding? = null

    lateinit var viewModel : ArtViewModel


    //TODO: Sola ve ya Sağa yöne swipe ettiğimizde silinecek.
    private val swipeCallBack = object  : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition //TODO: Kaçıncı sırayı seçti
            val selectedArt = artRecyclerAdapter.artList[layoutPosition] //TODO: Seçilen art hangisi onu alabilirim.
            viewModel.deleteArt(selectedArt)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding  = FragmentArtsBinding.bind(view)
        _binding = binding

        subscribeToObservers()


        binding.recyclerViewArt.adapter = artRecyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.artList = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}