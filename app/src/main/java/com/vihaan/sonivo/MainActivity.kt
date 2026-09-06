package com.vihaan.sonivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.rememberDrawerState
import com.vihaan.sonivo.navigation.Home
import com.vihaan.sonivo.navigation.Library
import com.vihaan.sonivo.navigation.NavKey
import com.vihaan.sonivo.navigation.NowPlaying
import com.vihaan.sonivo.navigation.Search
import com.vihaan.sonivo.ui.ViewModelFactory
import com.vihaan.sonivo.ui.home.HomeScreen
import com.vihaan.sonivo.ui.library.LibraryScreen
import com.vihaan.sonivo.ui.nowplaying.NowPlayingScreen
import com.vihaan.sonivo.ui.search.SearchScreen
import com.vihaan.sonivo.ui.theme.SonivoTheme

import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = (application as SonivoApp).musicRepository
        val playbackConnection = (application as SonivoApp).playbackConnection
        val factory = ViewModelFactory(repository, playbackConnection)

        setContent {
            SonivoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    colors = androidx.tv.material3.SurfaceDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    MainScreen(factory)
                }
            }
        }
    }
}

@Composable
fun MainScreen(factory: ViewModelFactory) {
    val backStack = remember { mutableStateListOf<Any>(Home) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val navigationItems = listOf(
        NavigationItem("Home", Home, Icons.Rounded.Home),
        NavigationItem("Search", Search, Icons.Rounded.Search),
        NavigationItem("Library", Library, Icons.Rounded.LibraryMusic),
        NavigationItem("Now Playing", NowPlaying, Icons.Rounded.PlayArrow)
    )

    NavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            navigationItems.forEach { item ->
                NavigationDrawerItem(
                    selected = backStack.lastOrNull() == item.key,
                    onClick = {
                        if (backStack.lastOrNull() != item.key) {
                            backStack.clear()
                            backStack.add(item.key)
                        }
                    },
                    leadingContent = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    }
                ) {
                    Text(item.label)
                }
            }
        }
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { if (backStack.size > 1) backStack.removeLast() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen(viewModel(factory = factory))
                }
                entry<Search> {
                    SearchScreen(viewModel(factory = factory))
                }
                entry<Library> {
                    LibraryScreen(viewModel(factory = factory))
                }
                entry<NowPlaying> {
                    NowPlayingScreen(viewModel(factory = factory))
                }
            }
        )
    }
}

data class NavigationItem(val label: String, val key: NavKey, val icon: ImageVector)

@Preview(device = "id:tv_1080p")
@Composable
fun MainScreenPreview() {
    SonivoTheme {
        Text("Main Screen Preview")
    }
}
