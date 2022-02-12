package com.example.childmanager.ui.activity

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.childmanager.R
import com.example.childmanager.arch.ui.base.activity.BaseActivity
import com.example.childmanager.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val navController: NavController by lazy { findNavController(R.id.mainHostNavFragment) }
}