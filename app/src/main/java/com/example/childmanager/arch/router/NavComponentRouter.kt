package com.example.childmanager.arch.router

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.*
import com.example.childmanager.arch.router.command.Command
import com.example.childmanager.arch.router.command.NavDirection
import java.util.logging.Level
import java.util.logging.Logger

open class NavComponentRouter(
    private val logger: Logger,
    private val navController: NavController,
) : Router {

    override fun navigate(command: Command.Route) {
        if (command is NavDirection) {
            navigate(command)
        } else {
            throw Throwable(message = Router.UNKNOWN_COMMAND)
        }
    }

    override fun popBackStack() = navController.popBackStack()

    private fun navigate(command: NavDirection) {
        when (command) {
            is NavDirection.Direction -> navigateDirection(command.directions, command.options)
            is NavDirection.Replace -> navigateWithReplace(command.directions)
            is NavDirection.ResID -> navigateResId(command.resID, command.args)
            is NavDirection.BackTo -> popBackStack(command.destinationId, command.inclusive)
        }
    }

    open fun navigateDirection(directions: NavDirections, options: NavOptions? = null) {
        try {
            navController.navigate(directions, options)
        } catch (e: Exception) {
            catchException(e)
        }
    }

    open fun navigateWithReplace(directions: NavDirections) {
        navigateDirection(directions, replaceOption())
    }

    open fun navigateResId(@IdRes resId: Int, args: Bundle? = null) {
        try {
            navController.navigate(resId, args, navOptions {
                getAnimBuilder()
            })
        } catch (e: Exception) {
            catchException(e)
        }
    }

    open fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean) =
        navController.popBackStack(destinationId, inclusive)

    private fun replaceOption(): NavOptions? {
        val currentID = navController.currentDestination?.id ?: return null
        return navOptions {
            popUpTo(id = currentID, popUpToBuilder = { inclusive = true })
        }
    }

    private fun catchException(e: Exception) {
        logger.log(Level.WARNING, e.message)
    }

    private fun getAnimBuilder(): AnimBuilder = AnimBuilder()

}