package com.example.socialapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Load
import com.example.socialapp.R
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentHomeBinding
import com.example.socialapp.model.PostX
import com.example.socialapp.viewmodel.PostDetailViewModel
import com.example.socialapp.viewmodel.PostViewModel
import com.example.socialapp.viewmodel.UserViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(),PostAdapter.IPost {
    private lateinit var postViewModel: PostViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: PostAdapter
    private lateinit var postDetailViewModel: PostDetailViewModel
    private val posts = arrayListOf<PostX>()
    private var page: Int = 0
    private var isLastPage = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initBinding() {
        super.initBinding()
        Load.showLoading(this.context)
        binding.ivAvatar.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUserFragment()
            findNavController().navigate(action)
        }

        binding.barCreatPost.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner, {

            if (it== null) {
                binding.ivAvatar.setImageResource(R.mipmap.ic_launcher)

            } else {
                binding.avtUser = it.data
//                Log.d("avatar", "${it.data.avatar}")
            }
        })

        postViewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        postViewModel.getPostFirst(page.toString())
        postViewModel.dataPost.observe(viewLifecycleOwner, {
          if(it!=null){
              posts.clear()
              posts.addAll(it.data.posts)
              adapter.notifyDataSetChanged()
          }else{
              return@observe
          }
        Load.hideLoading()
        })






        adapter = PostAdapter(posts,this)
        binding.rcPost.adapter = adapter
        binding.rcPost.layoutManager =
            LinearLayoutManager(HomeFragment().context, LinearLayoutManager.VERTICAL, false)
        val visibleItemCount = intArrayOf(0)
        val totalItemCount = intArrayOf(0)
        val pastVisiblesItems = intArrayOf(0)
        binding.rcPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount[0] = binding.rcPost.childCount
                    totalItemCount[0] = binding.rcPost.layoutManager!!.itemCount
                    pastVisiblesItems[0] =
                        (binding.rcPost.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (isLastPage) {
                        return
                    } else {
                        if (visibleItemCount[0] + pastVisiblesItems[0] >= totalItemCount[0]) {
                            loadMore()
                            Log.d("loadmore","load")
                        }
                    }
                }
            }
        })

    }

    fun loadMore() {
        page += 1
        postViewModel.getPostPage(page.toString())
        postViewModel.dataPost.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.data.posts.size == 0) {
                    isLastPage = true
                } else {
//                posts.clear()
                    posts.addAll(it.data.posts)
                    adapter.notifyDataSetChanged()
                    isLastPage = false
                }
            } else {
                return@observe
            }
        })
    }

    override fun onItemClick(item: PostX?, position: Int?) {
        Toast.makeText(this.context,"click",Toast.LENGTH_SHORT).show()
        Log.d("click","click ${position} +${item.toString()}")

        val bundle= Bundle()
        bundle.putParcelable("postX",item)
        findNavController().navigate(R.id.action_homeFragment_to_postDetailFragment,bundle)

    }

    override fun onClickAvt(item: PostX?) {
        Log.d("avatar","${item.toString()}")
    }


}