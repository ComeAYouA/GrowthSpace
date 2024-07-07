package comeayoua.growthspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import comeayoua.growthspace.navigation.AppUiState
import comeayoua.growthspace.navigation.GrowthSpaceNavHost
import comeayoua.growthspace.navigation.rememberAppUiState
import comeayoua.growthspace.ui.theme.GrowthSpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect()
        }

        splashScreen.setKeepOnScreenCondition{
            viewModel.uiState.value is MainScreenUiState.Loading
        }

        enableEdgeToEdge()

        setContent {
            GrowthSpaceTheme(
                dynamicColor = false
            ) {
                val uiState = viewModel.uiState.collectAsState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    uiState.value.let { state ->
                        if (state is MainScreenUiState.IsReady){
                            MainScreen(
                                modifier = Modifier.padding(innerPadding),
                                appUiState = rememberAppUiState(
                                    windowSizeClass = calculateWindowSizeClass(activity = this),
                                    userData = state.userData
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    appUiState: AppUiState
){
    GrowthSpaceNavHost(
        modifier = modifier,
        appUiState = appUiState
    )
}
