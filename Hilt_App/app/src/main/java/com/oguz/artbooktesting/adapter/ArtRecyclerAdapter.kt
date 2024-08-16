package com.oguz.artbooktesting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.databinding.ArtRowBinding
import com.oguz.artbooktesting.model.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(

    val glide : RequestManager //TODO : Görseli göstereceğiz burada Glide o sebeple kullanıyoruz.

) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>()


{

    class ArtViewHolder(var binding : ArtRowBinding) : RecyclerView.ViewHolder(binding.root)

    //TODO: İki liste arasındaki farkı hesaplayan ve böylece
    // recyclerViewın gerekli kısımlarını güncelleyen bir sınıf Asynckron calısır.
    //TODO:100 tane ıtem var örnegin sadece 10 tanesini güncelleyor. RecyclerView i verimli hale getiriyor.
    //TODO: Yani 2 tane Art arasındaki farklı hesaplıayacak içerik aynı mı diye bakıyor.
    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil) //TODO:Listemizi oluşutururken bunu kullabiliyoruz


    var artList : List<Art>
        get() = recyclerListDiffer.currentList //TODO: Güncel olan listeyi bana getir.
        set(value) = recyclerListDiffer.submitList(value) //TODO : Yeni listeyi buraya koyacağım o bakacak hesalıyacak bana verecek



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ArtViewHolder(ArtRowBinding.inflate(inflater,parent,false))
    }

    override fun getItemCount() =  artList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {

        /*
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowNameText)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearText)

         */

        val art = artList[position]

        holder.binding.apply {
            artRowNameText.text = "Name : ${art.name}"
            artRowArtistNameText.text = "Artist Name : ${art.artistName}"
            artRowYearText.text = "Year : ${art.year}"
            glide.load(art.imageUrl).into(artRowImageView)
        }
        /*
       holder.itemView.apply {


           nameText.text = "Name : ${art.name}"
           artistNameText.text = "Artist Name : ${art.artistName}"
           yearText.text = "Year : ${art.year}"
           glide.load(art.imageUrl).into(imageView)


        }

         */
    }
}