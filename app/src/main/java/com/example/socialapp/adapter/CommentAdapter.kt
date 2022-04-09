package com.example.socialapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Utils
import com.example.socialapp.databinding.ItemCommentBinding
import com.example.socialapp.model.PostX

class CommentAdapter (private val comments: ArrayList<PostX>) :RecyclerView.Adapter<CommentAdapter.ViewHolder>()  {
    class ViewHolder(val binding :ItemCommentBinding) :RecyclerView.ViewHolder(binding.root) {
            fun bind(postX: PostX){
                Utils.setAvt(binding.ivAvatar,postX.user.avatar)
                binding.tvName.text= postX.user.first_name
                binding.tvContent.text= postX.content.text
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
      return ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
       holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
      return comments.size
    }

    fun addComment(item:Any){
        comments.clear()
        comments.add(item as PostX)
    }
}