package com.example.childmanager.arch.coroutine.impl

import com.example.childmanager.arch.coroutine.CoroutineLauncher
import kotlinx.coroutines.*

class DefaultCoroutineLauncher(
    private val scope: CoroutineScope,
    handleFailure: ((Throwable) -> Unit)? = null,
) : CoroutineLauncher {

    override fun launch(supervisor: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(errorHandler) {
            if (supervisor) {
                supervisorScope {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            handleFailure?.invoke(exception)
        }
    }
}