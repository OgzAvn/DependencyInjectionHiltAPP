package com.oguz.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.oguz.artbooktesting.R
import com.oguz.artbooktesting.databinding.ArtRowBinding
import com.oguz.artbooktesting.databinding.ImageRowBinding
import com.oguz.artbooktesting.model.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(

    val glide : RequestManager

):RecyclerView.Adapter<ImageRecyclerAdapter.ImageRecyclerView>(){

    class ImageRecyclerView(var binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

    //TODO: Url i içerisine alsın diye String yapıyorum.
    private var onItemClickListener : ((String) -> Unit)? = null


    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil) //TODO:Listemizi oluşuturuekn bunu kullabiliyoruz


    var imageList : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerView {

        val inflater = LayoutInflater.from(parent.context)
        return ImageRecyclerView(ImageRowBinding.inflate(inflater, parent, false))

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageRecyclerView, position: Int) {

        //val imageView = holder.itemView.findViewById<ImageView>(R.id.singleArtImageView)
        val url = imageList[position]
        holder.binding.apply {
            glide.load(url).into(singleArtImageView)
            /*
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
            */
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(url) //TODO: Üzerine tıklanan görselin url sini bu yöntem ile alabiliriz.
            }
        }

    }
}