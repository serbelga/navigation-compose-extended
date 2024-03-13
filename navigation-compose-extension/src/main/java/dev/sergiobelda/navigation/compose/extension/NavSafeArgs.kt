package dev.sergiobelda.navigation.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.StringType

@Composable
fun <K : NavArgumentKey> rememberNavSafeArgs(
    destination: NavDestination<K>,
    navBackStackEntry: NavBackStackEntry,
): NavSafeArgs<K> =
    remember(navBackStackEntry) {
        NavSafeArgs(destination, navBackStackEntry)
    }

class NavSafeArgs<K>(
    private val destination: NavDestination<K>,
    private val navBackStackEntry: NavBackStackEntry,
) where K : NavArgumentKey {

    private val argumentValues: Map<String, Any?> = buildArgumentValuesMap()

    private fun buildArgumentValuesMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        navBackStackEntry.arguments?.apply {
            destination.arguments.forEach { namedNavArgument ->
                map[namedNavArgument.name] = when (namedNavArgument.argument.type) {
                    StringType -> getString(namedNavArgument.name)
                    IntType -> getInt(namedNavArgument.name)
                    else -> null
                }
            }
        }
        return map
    }

    fun getString(key: K): String? =
        argumentValues.getValue(key.argumentKey) as? String
}
