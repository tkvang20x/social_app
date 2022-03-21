package com.example.socialapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.socialapp.MainActivity

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    protected lateinit var binding: VB

    private lateinit var myInflater: LayoutInflater
    private lateinit var callback: OnBackPressedCallback

    protected val activityOwner by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(requireActivity())
        }
        binding = DataBindingUtil.inflate(myInflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding()
        return binding.root
    }

    abstract fun getLayoutId(): Int

    open fun onBackPressed(): Boolean {
        return false
    }


    open fun initBinding() {}

}
