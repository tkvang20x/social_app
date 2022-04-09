package com.example.socialapp.fragment.loginregister

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.socialapp.Const
import com.example.socialapp.MainActivity
import com.example.socialapp.R
import com.example.socialapp.SharedPreference.MySharedPreference
import com.example.socialapp.base.BaseFragment
import com.example.socialapp.databinding.FragmentLoginBinding
import com.example.socialapp.model.login.Login
import com.example.socialapp.viewmodel.LoginViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    lateinit var model: LoginViewModel
    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    //private lateinit var navController : NavController
    override fun initBinding() {
        super.initBinding()

        model = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            var login = Login(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())

            if (binding.edtEmail.text.toString() != "" && binding.edtPassword.text.toString() != "") {

                model.postLogin(login)
                model.dataLogin.observe(this, {
//                    Log.d("zz","xxx${it.message}")
                    if (it.message == "AUTH.LOGIN.FAILED") {
                        Toast.makeText(this.context,"Nhập sai thông tin", Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("kkk", "abc ${it}")
                        MySharedPreference(requireActivity()).putToken(it.data.access_token)

                        Const.TOKEN = MySharedPreference(requireActivity()).getToken().toString()

                        val intent = Intent()
                        intent.setClass(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    }

                })
            } else {
                Toast.makeText(this.context, "Điền thiếu thông tin", Toast.LENGTH_LONG)
                    .show()
                Log.d("zz","xx${login}")

            }
        }
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }
    }
}