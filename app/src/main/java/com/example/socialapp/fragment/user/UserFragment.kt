package com.example.socialapp.fragment.user

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.viewmodel.UserViewModel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.Load
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.databinding.FragmentUserBinding
import com.example.socialapp.model.PostX
import com.example.socialapp.viewmodel.PostViewModel
import com.example.socialapp.viewmodel.ProfileViewModel


class UserFragment : BaseFragment<FragmentUserBinding>(), PostAdapter.IPost {
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileUserViewModel: ProfileViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostAdapter
    private var page: Int = 0
    private val posts = arrayListOf<PostX>()
    private var isLastPage = false
    private var id: String = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }

    override fun initBinding() {
        super.initBinding()
        Load.showLoading(this.context)

        binding.tvMore.setOnClickListener { view ->
//            val fragmentManager = fragmentManager
//            val fragmentTransaction =
//                fragmentManager!!.beginTransaction()
//            fragmentTransaction.replace(
//                R.id.flMain,
//                ProfileFragment(),
//                ProfileFragment::class.java.toString()
//            )
//            fragmentTransaction.commit()
            val action =
                UserFragmentDirections.actionUserFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        binding.btnPost.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_createPostFragment)
        }


        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it.data
                id = it.data._id.toString()
            }
        })


        profileUserViewModel =
            ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        profileUserViewModel.getDataProfile()
        profileUserViewModel.data2.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.profile = it.data
//        Log.d("high","highscholl:${it.data.hight_school}")
                if (it.data.hight_school?.name != "") {
                    binding.tvHighSchool.visibility = View.VISIBLE
                } else {
                    binding.tvHighSchool.visibility = View.GONE
                }

                if (it.data.university?.name != "") {
                    binding.tvUniversity.visibility = View.VISIBLE
                } else {
                    binding.tvUniversity.visibility = View.GONE
                }

            }
        })
//        Load().hideLoading()

        postViewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
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
        binding.rcPostPersonal.adapter = adapter
        binding.rcPostPersonal.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val visibleItemCount = intArrayOf(0)
        val totalItemCount = intArrayOf(0)
        val pastVisiblesItems = intArrayOf(0)
        binding.rcPostPersonal.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount[0] = binding.rcPostPersonal.childCount
                    totalItemCount[0] = binding.rcPostPersonal.layoutManager!!.itemCount
                    pastVisiblesItems[0] =
                        (binding.rcPostPersonal.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    if (isLastPage) {
                        return
                    } else {
                        if (visibleItemCount[0] + pastVisiblesItems[0] >= totalItemCount[0]) {
                            loadMore()
                            Log.d("loadmore", "load")
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
                    adapter.notifyDataSetChanged()
                    isLastPage = false
                }
            } else {
                return@observe
            }
        })
    }

    override fun onItemClick(item: PostX?, position: Int?) {
        val bundle = Bundle()
        bundle.putParcelable("postX", item)
        findNavController().navigate(R.id.action_userFragment_to_postDetailFragment, bundle)
    }

    override fun onClickAvt(item: PostX?) {

    }
}




