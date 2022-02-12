package com.example.childmanager.arch.ui.base.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.childmanager.arch.coroutine.CoroutineLauncher
import com.example.childmanager.arch.coroutine.impl.DefaultCoroutineLauncher
import com.example.childmanager.arch.lifecycle.SingleLiveEvent
import com.example.childmanager.ui.view.TextProvider
import com.example.childmanager.arch.provider.CoroutineProvider
import com.example.childmanager.arch.router.command.Command
import kotlinx.coroutines.CoroutineScope

abstract class BaseViewModel : ViewModel(), CoroutineProvider {

    override val launcher: CoroutineLauncher by lazy {
        DefaultCoroutineLauncher(
            viewModelScope,
            ::handleCoroutineFailure
        )
    }

    private val _message by lazy { SingleLiveEvent<TextProvider>() }
    val message: LiveData<TextProvider> get() = _message

    private val _navigationCommand by lazy { SingleLiveEvent<Command>() }
    val navigationCommand: LiveData<Command> get() = _navigationCommand

    fun launch(block: suspend CoroutineScope.() -> Unit) = launcher.launch(block = block)

    open fun handleCoroutineFailure(throwable: Throwable) {}
}