package com.example.socialapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Utils
import com.example.socialapp.databinding.ItemImageBinding
import com.example.socialapp.viewmodel.CreatePostViewModel
import okhttp3.MultipartBody

class ImageAdapter2( val listImage: List<Uri>) :RecyclerView.Adapter<ImageAdapter2.ViewHolder>() {
    class ViewHolder(val binding:ItemImageBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Uri){
            Utils.loadImageFromUri(binding.imgImage,image.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter2.ViewHolder {
return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ImageAdapter2.ViewHolder, position: Int) {
      holder.bind(listImage[position])
    }

    override fun getItemCount(): Int {
       return listImage.size
    }
}