package com.example.socialapp.fragment.post

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.socialapp.R
import com.example.socialapp.Utils
import com.example.socialapp.adapter.ImageAdapter2
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentCreatePostBinding
import com.example.socialapp.model.Content
import com.example.socialapp.model.ImageUpload
import com.example.socialapp.model.ItemImage
import com.example.socialapp.model.creatpost.CreatePost
import com.example.socialapp.viewmodel.CreatePostViewModel
import com.example.socialapp.viewmodel.UserViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList

class CreatePostFragment : BaseFragment<FragmentCreatePostBinding>() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var createPostViewModel: CreatePostViewModel
    private lateinit var imageAdapter: ImageAdapter2
    private val images = ArrayList<MultipartBody.Part>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_create_post
    }

    override fun initBinding() {
        super.initBinding()

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getDataUser()
        userViewModel.data1.observe(viewLifecycleOwner, {
            Utils.setAvt(binding.ivAvatar, it.data.avatar)
            binding.tvName.text = it.data.full_name
        })


        binding.btnPhoto.setOnClickListener {
            checkPermission()
        }

        binding.btnCreat.setOnClickListener {
            createPostViewModel = ViewModelProvider(requireActivity()).get(CreatePostViewModel::class.java)
            createPostViewModel.uploadImage(images)
            Log.d("datalistimage", "${images.toString()}")
            createPostViewModel.imageUpload.observe(viewLifecycleOwner, {
                Log.d("imageUpload", createPostViewModel.getImageUpload(it).toString())
                if (it == null) {
                    val createPost = CreatePost(
                        Content(
                            null,
                            null,
                            null,
                            binding.edtContent.text.toString()
                        )
                    )
                    createPostViewModel.createPost(createPost)
                    createPostViewModel.postSucces.observe(viewLifecycleOwner, {
                        if (it != null) {
                            val bundle = Bundle()
                            bundle.putParcelable("post", it.data)
                            findNavController(binding.root).navigate(R.id.homeFragment, bundle)
                        } else {
                            Toast.makeText(
                                this@CreatePostFragment.context,
                                "Có lỗi xảy ra!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })


                } else {
                    val createPost = CreatePost(
                        Content(createPostViewModel.getImageUpload(it), null, null, binding.edtContent.text.toString())
                    )

                    createPostViewModel.createPost(createPost)
                    createPostViewModel.postSucces.observe(viewLifecycleOwner, {
                        if (it != null) {
                            val bundle = Bundle()
                            bundle.putParcelable("post", it.data)
                            findNavController(binding.root).navigate(R.id.homeFragment, bundle)
                        } else {
                            Toast.makeText(
                                this@CreatePostFragment.context,
                                "Có lỗi xảy ra!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            })
        }
    }

    fun checkPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                TedBottomPicker.with(activity)
                    .setPeekHeight(1600)
                    .showTitle(false)
                    .setCompleteButtonText("Done")
                    .setEmptySelectionText("No Select")
                    .showMultiImage {
                        for (i in 0 until it.size) {
                            images.add(getPath(it[i]))
                        }
                        Log.d("image", " ${images.toString()}")

                        imageAdapter = ImageAdapter2(it)
                        imageAdapter.notifyDataSetChanged()
                        binding.rcImage.adapter= imageAdapter
                        binding.rcImage.layoutManager= GridLayoutManager(CreatePostFragment().context,2)

                    }
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    context,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
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