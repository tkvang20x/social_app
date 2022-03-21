package com.example.socialapp.fragment.userbyid

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.socialapp.R
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentUserByIdBinding

import com.example.socialapp.viewmodel.PostViewModel
import com.example.socialapp.viewmodel.ProfileViewModel
import com.example.socialapp.viewmodel.UserViewModel

class UserByIdFragment : BaseFragment<FragmentUserByIdBinding>() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileUserViewModel: ProfileViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var adapter: PostAdapter
    private lateinit var id:String
    override fun getLayoutId(): Int {
        return R.layout.fragment_user_by_id
    }

    override fun initBinding() {
        super.initBinding()

        if(arguments!=null){
            id = arguments!!.get("id").toString()
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
                if(it.data.hight_school!=null  && it.data.hight_school?.name!=""){
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
    }
}