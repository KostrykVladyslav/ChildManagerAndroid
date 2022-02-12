package com.example.childmanager.ui.fragment.login

import android.os.Bundle
import android.view.View
import com.example.childmanager.R
import com.example.childmanager.arch.ui.base.fragment.BaseFragment
import com.example.childmanager.databinding.FragmentLoginBinding
import com.example.childmanager.ui.view.TextProvider

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMessage(TextProvider.Text("Is working"))
    }

}