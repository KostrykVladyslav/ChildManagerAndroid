package com.example.childmanager.arch.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.childmanager.arch.ktx.getString
import com.example.childmanager.arch.provider.RouterProvider
import com.example.childmanager.arch.router.NavComponentRouter
import com.example.childmanager.arch.router.Router
import com.example.childmanager.arch.router.command.Command
import com.example.childmanager.arch.router.command.NavDirection
import com.example.childmanager.arch.ui.base.view_model.BaseViewModel
import com.example.childmanager.ui.view.TextProvider
import java.util.logging.Logger

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutID: Int,
) : Fragment(), RouterProvider {

    protected lateinit var binding: T private set

    private val logger = Logger.getGlobal()

    override val router: Router by lazy {
        NavComponentRouter(
            navController = findNavController(),
            logger = logger
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayoutID, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setBaseObservers()
        setObservers()
    }

    protected open fun setObservers() {}

    protected open fun setBaseObservers() {
        getViewModel()?.let {
            it.navigationCommand.observe(viewLifecycleOwner, ::navigate)
            it.message.observe(viewLifecycleOwner, ::showMessage)
        }
    }

    protected open fun navigate(command: Command) {
        when (command) {
            is NavDirection -> router.navigate(command)
            Command.Back -> router.popBackStack()
            Command.Close -> requireActivity().finish()
        }
    }

    protected open fun showMessage(textProvider: TextProvider) {
        context?.let { context ->
            Toast.makeText(
                context.applicationContext,
                textProvider.getString(context),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    protected open fun getViewModel(): BaseViewModel? = null
}