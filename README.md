# Navigation Compose extension

## Usage

### Without arguments

```kotlin
    object ArtistsNavDestination : NavDestination() {
    override val destinationId: String = "artists"
}

NavHost(startDestination = ArtistsNavDestination.route) {
    composable(route = ArtistsNavDestination.route) { ... }
}

object ArtistsNavRoute : NavRoute(ArtistsNavDestination)

action.navigate(ArtistsNavRoute)
```

### Defining arguments

```kotlin
    object ArtistDetailsNavDestination : NavDestination() {
    override val destinationId: String = "artistdetails"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument("artistId") { type = NavType.StringType }
    )

    fun navArgArtistId(navBackStackEntry: NavBackStackEntry): String =
        navBackStackEntry.arguments?.getString("artistId") ?: ""
}

NavHost {
    composable(
        route = ArtistDetailsNavDestination.route,
        arguments = ArtistDetailsNavDestination.arguments
    ) { ... }
}

class ArtistDetailsNavRoute(artistId: Int) : NavRoute(ArtistDetailsNavDestination, artistId)

action.navigate(ArtistDetailsNavRoute(1))
```
