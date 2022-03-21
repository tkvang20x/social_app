package com.example.socialapp.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentProfileBinding
import com.example.socialapp.viewmodel.ProfileViewModel
import com.example.socialapp.viewmodel.UserViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var profileUserViewModel: ProfileViewModel
    val userId ="61c1cee14106440200b6a111"
    override fun getLayoutId(): Int {
       return R.layout.fragment_profile
    }

    override fun initBinding() {
        super.initBinding()

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvUpdateProfile.setOnClickListener {
            val firtName= binding.txtfirstName.text.toString()
            val lastName= binding.txtlastName.text.toString()
            val gender= binding.txtgender.text.toString()

            val highSchool= binding.tvHighSchool.text.toString()
            val university= binding.tvUniversity.text.toString()

            val action=ProfileFragmentDirections.actionProfileFragmentToUpdateProfile(firtName,lastName,gender,highSchool,university)
            findNavController().navigate(action)
        }


        userViewModel = UserViewModel()
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner,  {
            if (it != null) {
                binding.user=it.data
                Log.d("user","${it.data.full_name}")
            }
        })

        profileUserViewModel= ProfileViewModel()
        profileUserViewModel.getDataProfile()
        profileUserViewModel.data2.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.profile=it.data
            }
        })

        binding.tvUpdateAvt.setOnClickListener {
            checkPermission()
        }

    }

    private fun checkPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                TedBottomPicker.with(activity).show { uri ->
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Thay Avatar")
                    builder.setMessage("Bạn Có Muốn Xác Nhận Thay Avatar?")
                    builder.setNegativeButton(
                        "No"
                    ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
                    builder.setPositiveButton(
                        "Yes"
                    ) { dialogInterface: DialogInterface?, i: Int ->
                        var bitmap: Bitmap? = null
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                activity!!.contentResolver,
                                uri
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        binding.imgAvt.setImageBitmap(bitmap)
                        val multipartBody: MultipartBody.Part = getPath(uri)
                        userViewModel.uploadAvatar(userId, multipartBody)
                        val builderSuccess =
                            AlertDialog.Builder(requireContext())
                        builderSuccess.setTitle("Thay Avatar")
                        builderSuccess.setMessage("Thay Avatar Thành Công")
                        builderSuccess.show()
                    }
                    builder.show()
                }
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()
    }
    fun getPath(uri: Uri): MultipartBody.Part {
        val file = File(uri.path)
        val reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData("avatar", file.name, reqFile)
    }
}