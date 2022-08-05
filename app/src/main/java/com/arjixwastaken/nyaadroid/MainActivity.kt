package com.arjixwastaken.nyaadroid

import android.annotation.SuppressLint
import com.arjixwastaken.nyaadroid.ui.theme.NyaaDroidTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.arjixwastaken.nyaadroid.screens.*
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.lagradost.nicehttp.Requests
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.*
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arjixwastaken.nyaadroid.api.RetrieveTorrents
import com.arjixwastaken.nyaadroid.api.TorrentResult
import com.arjixwastaken.nyaadroid.components.TorrentItem
import com.arjixwastaken.nyaadroid.utils.Coroutines.main
import kotlinx.coroutines.launch

val app by lazy { Requests() }
// TODO: Figure out how networking works

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NyaaDroidTheme {
                MainScreen()
            }
        }
    }
}

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, @DrawableRes val icon: Int) {
    object Home : BottomNavigationScreens("Home", R.string.home_route, R.drawable.ic_baseline_home_24)
    object Browse : BottomNavigationScreens("Browse", R.string.browse_route, R.drawable.ic_baseline_search_24)
    object Settings : BottomNavigationScreens("Settings", R.string.settings_route, R.drawable.ic_baseline_settings_24)
}


@Composable
fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) { Home() }
        composable(BottomNavigationScreens.Browse.route) { Browse( remember { BrowseItems }) }
        composable(BottomNavigationScreens.Settings.route) { Settings() }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Browse,
        BottomNavigationScreens.Settings,
    )
    Scaffold(
        bottomBar = { BottomNavigation(navController, bottomNavigationItems) },
    ) { MainScreenNavigationConfigurations(navController) }
}
