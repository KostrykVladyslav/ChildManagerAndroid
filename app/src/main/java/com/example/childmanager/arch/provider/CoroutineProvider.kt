package com.example.childmanager.arch.provider

import com.example.childmanager.arch.coroutine.CoroutineLauncher

interface CoroutineProvider {
    val launcher: CoroutineLauncher
}