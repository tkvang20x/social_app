package com.example.socialapp.fragment.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.R
import com.example.socialapp.adapter.CommentAdapter
import com.example.socialapp.adapter.ImageAdapter
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentPostDetail2Binding
import com.example.socialapp.model.Content
import com.example.socialapp.model.comment.PostComment
import com.example.socialapp.viewmodel.CommentViewModel
import com.example.socialapp.model.PostX
import com.example.socialapp.viewmodel.PostViewModel

class PostDetailFragment : BaseFragment<FragmentPostDetail2Binding>() {
    private lateinit var postX: PostX
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var id: String
    private lateinit var post_id: String
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var postViewModel: PostViewModel
    private var comments = arrayListOf<PostX>()
    private lateinit var id_user:String
    override fun getLayoutId(): Int {
        return R.layout.fragment_post_detail_2
    }

    override fun initBinding() {
        super.initBinding()

        if (arguments != null) {
            postX = arguments!!.getParcelable("postX")!!
            id_user = arguments!!.getString("id_user").toString()
            binding.item = postX
            post_id = postX._id.toString()

            imageAdapter = ImageAdapter(postX.content.image as ArrayList<String>)
            binding.rvImage.adapter = imageAdapter
            binding.rvImage.layoutManager = LinearLayoutManager(
                PostDetailFragment().context,
                LinearLayoutManager.VERTICAL,
                false
            )

            if (postX.isLike == true) {
                binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_like_true,
                    0,
                    0,
                    0
                )
            } else {
                binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_like_svgrepo_com,
                    0,
                    0,
                    0
                )
            }
        }

        binding.btnLike.setOnClickListener {
            postViewModel=PostViewModel()
            if (postX.isLike == false) {
                binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_true, 0, 0, 0)
                postX.isLike = true
            } else {
                binding.btnLike.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_svgrepo_com, 0, 0, 0)
                postX.isLike = false
            }

            if(postX.isLike==true){
                if(postX.total_like!!-1 ==0){
                    postX.total_like=0
                    binding.tvLike.text ="0"
                }else if(postX.total_like ==0) {
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



        commentViewModel = ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)
        commentViewModel.getComments(post_id)
        commentViewModel.comment.observe(viewLifecycleOwner, {
            if (it == null) {
                return@observe
            } else {
                comments = it as ArrayList<PostX>
                commentAdapter = CommentAdapter(comments)
                binding.rvCmt.adapter = commentAdapter
                binding.rvCmt.layoutManager = LinearLayoutManager(
                    PostDetailFragment().context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        })

        binding.btnSend.setOnClickListener {
            val text = binding.edtCmt.text.toString()
            if (text != "") {
                commentViewModel =
                    ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)
                commentViewModel.postComment(
                    PostComment(
                        Content(
                            null,
                            null,
                            "TEXT",
                            text.toString()
                        ), post_id
                    )
                )
                var commentPost = PostX(
                    post_id, Content(
                        emptyList(),
                        emptyList(),
                        "TEXT",
                        text.toString()
                    ), null, null, null, null, null, null, null, null, postX.user
                )
                commentViewModel.isCheckCmt.observe(viewLifecycleOwner, {
                    if (it) {
                        comments.add(commentPost)
//                        commentAdapter.notifyDataSetChanged()
//                        commentAdapter.addComment(commentPost)
                        commentAdapter.notifyItemInserted(commentAdapter.itemCount - 1)
                        binding.tvComment.text = "${commentAdapter.itemCount.toString()} bình luận"
                        binding.edtCmt.setText("")
                        closeKeyboard()
                    }
                })

            } else {
                Toast.makeText(
                    PostDetailFragment().context,
                    "Bạn phải nhập nội dung",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.btnBefore.setOnClickListener {
           findNavController().popBackStack()
        }

        binding.ivAvatar.setOnClickListener {
            postX = arguments!!.getParcelable("postX")!!
            id = postX.user_id.toString()

            if(id == id_user){
                findNavController().navigate(R.id.action_postDetailFragment_to_userFragment)
            }else{
                val bundle = Bundle()
                bundle.putString("id", id)
                findNavController().navigate(R.id.action_postDetailFragment_to_userByIdFragment, bundle)
            }

        }


    }



    private fun closeKeyboard() {
        val view = this.activity!!.currentFocus
        if (view != null) {
            val imm =
                this.activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}