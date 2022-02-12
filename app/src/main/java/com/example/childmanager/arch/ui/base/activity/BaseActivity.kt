package com.example.childmanager.arch.ui.base.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.childmanager.arch.coroutine.impl.DefaultCoroutineLauncher
import com.example.childmanager.arch.ktx.getString
import com.example.childmanager.arch.provider.CoroutineProvider
import com.example.childmanager.arch.provider.RouterProvider
import com.example.childmanager.arch.router.NavComponentRouter
import com.example.childmanager.arch.router.Router
import com.example.childmanager.arch.router.command.Command
import com.example.childmanager.arch.router.command.NavDirection
import com.example.childmanager.arch.ui.base.view_model.BaseViewModel
import com.example.childmanager.ui.view.TextProvider
import java.util.logging.Logger

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutID: Int,
) : AppCompatActivity(), RouterProvider, CoroutineProvider {

    protected lateinit var binding: T private set

    protected abstract val navController: NavController

    private val logger: Logger = Logger.getGlobal()

    override val router: Router by lazy {
        NavComponentRouter(
            navController = navController,
            logger = logger
        )
    }

    override val launcher by lazy { DefaultCoroutineLauncher(lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutID)
        binding.lifecycleOwner = this
        setBaseObservers()
        setObservers()
    }

    protected open fun navigate(command: Command) {
        when (command) {
            is NavDirection -> router.navigate(command)
            Command.Back -> router.popBackStack()
            Command.Close -> finish()
        }
    }

    protected open fun setBaseObservers() {
        getViewModel()?.let {
            it.navigationCommand.observe(this, ::navigate)
            it.message.observe(this, ::showMessage)
        }
    }

    protected open fun showMessage(textProvider: TextProvider) {
        Toast.makeText(
            this,
            textProvider.getString(this),
            Toast.LENGTH_SHORT
        ).show()
    }

    protected open fun getViewModel(): BaseViewModel? = null

    protected open fun setObservers() {}
}
