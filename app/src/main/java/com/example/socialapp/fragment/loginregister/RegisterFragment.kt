package com.example.socialapp.fragment.loginregister

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.socialapp.R
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentRegisterBinding
import com.example.socialapp.model.register.DataRegister
import com.example.socialapp.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    lateinit var model: RegisterViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    @SuppressLint("ResourceAsColor")
    override fun initBinding() {
        super.initBinding()

        model = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
        binding.btnRegister.setOnClickListener {
            var mDataRegister = DataRegister(
                binding.edtName.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtName.text.toString(),
                binding.edtPass.text.toString()
            )
            if (binding.edtName.text.toString() != "" && binding.edtEmail.text.toString() != "" && binding.edtPhone.text.toString() != "" && binding.edtPass.text.toString() != "") {
                if (binding.edtName.text.toString().length > 4) {
                    if (binding.edtPass.text.toString().length < 8) {
                        Toast.makeText(
                            this.context,
                            "Mật khẩu phải lớn hơn 8 kí tự!!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        if (binding.checkbox.isChecked) {

                            if (binding.edtPass.text.toString() == binding.edtEnterPassword.text.toString()) {
                                model.postRegister(mDataRegister)
                                Log.d("aaa", "haix${mDataRegister}")
                                model.dataRegister.observe(viewLifecycleOwner, {

                                    when (it.message) {
                                        "AUTH.REGISTER.SUCCESSFULLY" -> DangKiThanhCong()
                                        "AUTH.REGISTER.CONFLICT_USERNAME" -> Toast.makeText(
                                            this.context,
                                            "Chùng tên!!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        "AUTH.REGISTER.CONFLICT_EMAIL" -> Toast.makeText(
                                            this.context,
                                            "Chùng email!!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        "AUTH.REGISTER.CONFLICT_PHONENUMBER" -> Toast.makeText(
                                            this.context,
                                            "Chùng số điện thoại!!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        "USER.CREATE.INVALID_PHONE_NUMBER" -> Toast.makeText(
                                            this.context,
                                            "Nhập sai số điện thoại!!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()

                                        else -> Toast.makeText(
                                            this.context,
                                            "Có lỗi không mong muốn vui lòng kiểm tra lại!!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()

                                    }
                                })
                            } else {
                                Toast.makeText(
                                    this.context,
                                    "Mật khẩu và mật khẩu nhập lại không chùng nhau!!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        } else {

                            Toast.makeText(
                                this.context,
                                "Bạn chưa đồng ý với điều khoản",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this.context,
                        "Tên phải lớn hơn 5 kí tự!!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } else {
                Toast.makeText(this.context, "Điền thiếu thông tin", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun DangKiThanhCong() {
        Toast.makeText(
            this.context,
            "Đăng kí thành công",
            Toast.LENGTH_LONG
        )
            .show()
        findNavController().popBackStack()
    }
}