package com.example.socialapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentUpdateProfileBinding
import com.example.socialapp.model.DataProfile
import com.example.socialapp.model.DataUser
import com.example.socialapp.model.school.HighSchool
import com.example.socialapp.model.school.University
import com.example.socialapp.viewmodel.ProfileViewModel
import com.example.socialapp.viewmodel.UserViewModel

class UpdateProfile : BaseFragment<FragmentUpdateProfileBinding>() {

    private val args: UpdateProfileArgs by navArgs()
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileViewModel: ProfileViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_update_profile
    }

    override fun initBinding() {
        super.initBinding()

        userViewModel = UserViewModel()
        profileViewModel= ProfileViewModel()

        binding.edtFirstName.setText(args.firstName)
        binding.edtLastName.setText(args.lastName)
        binding.edtGender.setText(args.gender)

        binding.edtHighSchool.setText(args.highSchool)
        binding.edtUniversity.setText(args.university)

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUpdate.setOnClickListener {
            updateInformation()
        }
    }

    @SuppressLint("ResourceType")
    private fun updateInformation() {
        val first_name = binding.edtFirstName.text.toString()
        val last_name = binding.edtLastName.text.toString()
        val gender = binding.edtGender.text.toString()

        val dataUser = DataUser(
            first_name,
            last_name,
            gender,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val high_schoolText=binding.edtHighSchool.text.toString()
        val universityText=binding.edtUniversity.text.toString()

        val highSchool =HighSchool(high_schoolText,null)
        val university =University(universityText,null)

        val dataProfile=DataProfile(highSchool,university,null,null,null,null,null,null,null,null)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sửa Thông Tin")
        builder.setMessage("Bạn Có Muốn Xác Nhận Sửa?")
        builder.setNegativeButton(("No"), { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() })
        builder.setPositiveButton(("Yes"), { dialogInterface:DialogInterface, i:Int ->
            userViewModel.updateDataUser(dataUser)
            profileViewModel.updateDataProfile(dataProfile)

            Log.d("xyz","${high_schoolText}+${universityText}")
            dialogInterface.dismiss()
            findNavController().popBackStack()
            val builder2 = AlertDialog.Builder(requireContext())
            builder2.setMessage("Sửa thành công!!!")
            builder2.show()
        })
        builder.show()

    }
}