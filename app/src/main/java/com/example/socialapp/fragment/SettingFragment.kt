package com.example.socialapp.fragment

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.socialapp.LoginActivity
import com.example.socialapp.MainActivity
import com.example.socialapp.R
import com.example.socialapp.SharedPreference.MySharedPreference
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentSettingBinding
import com.example.socialapp.viewmodel.UserViewModel

class SettingFragment:BaseFragment<FragmentSettingBinding>() {

    private lateinit var userViewModel: UserViewModel
    override fun getLayoutId(): Int {
       return R.layout.fragment_setting
    }

    override fun initBinding() {
        super.initBinding()

        binding.btnLogout.setOnClickListener {
            MySharedPreference(requireActivity()).clearToken()

            val intent = Intent()
            intent.setClass(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.user = it.data
            }
        })

        binding.ivAvatar.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_userFragment)
        }
    }
}