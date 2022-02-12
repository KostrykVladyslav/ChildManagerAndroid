package com.example.childmanager.arch.router

import com.example.childmanager.arch.router.command.Command

interface Router {

    fun navigate(command: Command.Route)

    fun popBackStack(): Boolean

    companion object {
        const val UNKNOWN_ROUTE = "Unknown for this route"
        const val UNKNOWN_COMMAND = "Unknown command"
    }
}