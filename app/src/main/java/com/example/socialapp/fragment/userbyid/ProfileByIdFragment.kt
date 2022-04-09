package com.example.socialapp.fragment.userbyid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.socialapp.Load
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentMoreProfileByIdBinding
import com.example.socialapp.viewmodel.ProfileViewModel
import com.example.socialapp.viewmodel.UserViewModel

class ProfileByIdFragment : BaseFragment<FragmentMoreProfileByIdBinding>() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var profileUserViewModel: ProfileViewModel
    private lateinit var id: String
    override fun getLayoutId(): Int {
        return R.layout.fragment_more_profile_by_id
    }

    override fun initBinding() {
        super.initBinding()
        Load.showLoading(this.context)
        if (arguments != null) {
            id = arguments!!.get("id").toString()
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileByIdFragment_to_userByIdFragment)
        }

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataById(id)
        userViewModel.data1.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.user = it.data
                Log.d("userid", "${it.toString()}")
            } else {
                return@observe
            }
        })

        profileUserViewModel =
            ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        profileUserViewModel.getProfileById(id)
        profileUserViewModel.data2.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.profile = it.data
                if (it.data.hight_school != null && it.data.hight_school?.name != "") {
                    binding.tvHighSchool.visibility = View.VISIBLE
                } else {
                    binding.tvHighSchool.visibility = View.GONE
                }

                if (it.data.university != null && it.data.university?.name != "") {
                    binding.tvUniversity.visibility = View.VISIBLE
                } else {
                    binding.tvUniversity.visibility = View.GONE
                }

            } else {
                return@observe
            }
            Load.hideLoading()

        })
    }
}