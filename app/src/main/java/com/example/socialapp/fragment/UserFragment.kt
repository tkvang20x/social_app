package com.example.socialapp.fragment

import android.util.Log
import android.view.View
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.viewmodel.UserViewModel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
            val action = UserFragmentDirections.actionUserFragmentToProfileFragment()
            findNavController().navigate(action)
        }


        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it.data
//                Log.d("user","${it.data.full_name}")
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

        postViewModel = PostViewModel()
        postViewModel.getPostFirst(page.toString())
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
    }

    override fun onItemClick(item: PostX?, position: Int?) {
        TODO("Not yet implemented")
    }

    override fun onClickAvt(item: PostX?) {
        TODO("Not yet implemented")
    }
}




