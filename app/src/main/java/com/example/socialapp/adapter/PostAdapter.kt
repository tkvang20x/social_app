package com.example.socialapp.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.createNavigateOnClickListener
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.R
import com.example.socialapp.Utils
import com.example.socialapp.callback.BaseListener
import com.example.socialapp.databinding.ItemPostBinding

import com.example.socialapp.fragment.HomeFragment
import com.example.socialapp.fragment.HomeFragmentDirections
import com.example.socialapp.fragment.userbyid.UserByIdFragment
import com.example.socialapp.model.PostX
import com.example.socialapp.viewmodel.PostViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import pereira.agnaldo.previewimgcol.ImageCollectionView


class PostAdapter(private val posts: ArrayList<PostX>, private val listener: IPost) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    class ViewHolder( val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var imageAdapter: ImageAdapter
        private lateinit var postViewModel: PostViewModel
        fun bind(postX: PostX) {
//            var container:ShimmerFrameLayout =binding.smFL
//            container.startShimmer()

            binding.post = postX

            var isLike = postX.isLike
            if(isLike==true){
                binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_true, 0, 0, 0)
            }else{
                binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_svgrepo_com, 0, 0, 0)
            }

            binding.btnLike.setOnClickListener {
                postViewModel=PostViewModel()
                if (isLike == false) {
                    binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_true, 0, 0, 0)
                    isLike = true
                } else {
                    binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_svgrepo_com, 0, 0, 0)
                    isLike = false
                }

                if(isLike==true){
                   if(postX.total_like ==0) {
                        binding.tvLike.text = postX.total_like!!.plus(1).toString()
                        postX.total_like=1
                    }else{
                        binding.tvLike.text = postX.total_like!!.plus(1).toString()
                        postX.total_like = (binding.tvLike.text as String).toInt()
                    }
                    postX.isLike =true
                    postViewModel.getLikePost(postX._id)

                }
                else{
                    postViewModel.getDislikePost(postX._id)
                    postX.isLike=false
                    if( postX.total_like!! -1<=0){
                        binding.tvLike.text = "0"
                        postX.total_like=0
                    }else{
                        binding.tvLike.text = postX.total_like!!.minus(1).toString()
                        postX.total_like = (binding.tvLike.text as String).toInt()
                    }

                }
                Log.d("total like","${postX.total_like}")

            }


            if (postX.content.image?.isEmpty() == true) {
                return
            } else {
//                if (postX.content.image?.size == 1) {
//                    imageAdapter = ImageAdapter(postX.content.image as ArrayList<String>)
//                    binding.rcImage.adapter = imageAdapter
//                    binding.rcImage.layoutManager = GridLayoutManager(HomeFragment().context, 1)
//                } else if (postX.content.image?.size!! >= 2) {
//                    imageAdapter = ImageAdapter(postX.content.image as ArrayList<String>)
//                    binding.rcImage.adapter = imageAdapter
//                    binding.rcImage.layoutManager = GridLayoutManager(HomeFragment().context, 2)
//                }
            }
        }
    }

    interface IPost {
        fun onItemClick(item: PostX?, position: Int?)
        fun onClickAvt(item: PostX?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(posts[position], position)
        }
        holder.binding.ivAvatar.setOnClickListener {
            listener.onClickAvt(posts[position])
        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.setIsRecyclable(false)
        super.onViewDetachedFromWindow(holder)
    }
override fun getItemViewType(position: Int): Int {
    return if (posts.get(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
}
}
