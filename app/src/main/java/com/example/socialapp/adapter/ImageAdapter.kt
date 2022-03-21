package com.example.socialapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Utils
import com.example.socialapp.databinding.ItemImageBinding

class ImageAdapter(private val listImage: ArrayList<String>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemImageBinding) :RecyclerView.ViewHolder(binding.root) {
            fun bind(image:String){
              Utils.setAvt(binding.imgImage,image)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
       return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
       holder.bind(listImage[position])
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}