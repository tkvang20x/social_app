package com.example.socialapp.fragment.userbyid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Load
import com.example.socialapp.R
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentUserByIdBinding
import com.example.socialapp.model.PostX

import com.example.socialapp.viewmodel.PostViewModel
import com.example.socialapp.viewmodel.ProfileViewModel
import com.example.socialapp.viewmodel.UserViewModel

class UserByIdFragment : BaseFragment<FragmentUserByIdBinding>() ,PostAdapter.IPost{
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileUserViewModel: ProfileViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostAdapter
    private var id:String=""
    private var page: Int = 0
    private val posts = arrayListOf<PostX>()
    private var isLastPage = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_user_by_id
    }

    override fun initBinding() {
        super.initBinding()

        Load.showLoading(this.context)
        if(arguments!=null){
            id = arguments!!.get("id").toString()
        }

        binding.tvMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", id)
            findNavController().navigate(R.id.action_userByIdFragment_to_profileByIdFragment, bundle)
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_userByIdFragment_to_homeFragment)
        }

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataById(id)
        userViewModel.data1.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.user=it.data
                Log.d("userid","${it.toString()}")
            }else{
                return@observe
            }
        })
        profileUserViewModel= ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        profileUserViewModel.getProfileById(id)
        profileUserViewModel.data2.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.profile=it.data
                if(it.data.hight_school !=null  && it.data.hight_school?.name!=""){
                    binding.tvHighSchool.visibility= View.VISIBLE
                }
                else{
                    binding.tvHighSchool.visibility= View.GONE
                }

                if(it.data.university !=null  && it.data.university?.name!="" ){
                    binding.tvUniversity.visibility= View.VISIBLE
                }else{
                    binding.tvUniversity.visibility= View.GONE
                }

            }else{
                return@observe
            }
        })

        postViewModel = PostViewModel()
        postViewModel.getPostByIdFirst(id,page.toString())
        postViewModel.dataPost.observe(viewLifecycleOwner, {
            if (it != null) {
                posts.clear()
                posts.addAll(it.data.posts)
                adapter.notifyDataSetChanged()
            } else {
                return@observe
            }
            Load.hideLoading()

        })

        adapter = PostAdapter(posts, this)
        binding.rcPostUserId.adapter = adapter
        binding.rcPostUserId.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val visibleItemCount = intArrayOf(0)
        val totalItemCount = intArrayOf(0)
        val pastVisiblesItems = intArrayOf(0)
        binding.rcPostUserId.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    Log.d("loadmore","${dy}")
                    visibleItemCount[0] = binding.rcPostUserId.childCount
                    totalItemCount[0] = binding.rcPostUserId.layoutManager!!.itemCount
                    pastVisiblesItems[0] =
                        (binding.rcPostUserId.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (isLastPage) {
                        return
                    } else {
                        if (visibleItemCount[0] + pastVisiblesItems[0] >= totalItemCount[0]) {
                            loadMore()
                        }
                    }
                }
            }
        })
    }


    fun loadMore() {
        page += 1
        postViewModel.getPostByIdPage(id,page.toString())
        postViewModel.dataPost.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.data.posts.size == 0) {
                    isLastPage = true
                } else {
//                posts.clear()
                    posts.addAll(it.data.posts)
                    adapter.notifyItemInserted(posts.size-1)
                    isLastPage = false
                }
            } else {
                return@observe
            }
        })

    }


    override fun onItemClick(item: PostX?, position: Int?) {
        val bundle= Bundle()
        bundle.putParcelable("postX",item)
        findNavController().navigate(R.id.action_userByIdFragment_to_postDetailFragment,bundle)
    }

    override fun onClickAvt(item: PostX?) {

    }
}