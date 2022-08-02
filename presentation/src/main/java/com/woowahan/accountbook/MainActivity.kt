package com.woowahan.accountbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.woowahan.accountbook.components.calendar.CalendarScreen
import com.woowahan.accountbook.components.history.HistoryScreen
import com.woowahan.accountbook.components.history.create.HistoryCreateScreen
import com.woowahan.accountbook.components.setting.SettingScreen
import com.woowahan.accountbook.components.setting.create.CategoryCreateScreen
import com.woowahan.accountbook.components.setting.create.PaymentMethodCreateScreen
import com.woowahan.accountbook.components.setting.mode.SettingMode
import com.woowahan.accountbook.components.statistics.StatisticsScreen
import com.woowahan.accountbook.domain.model.PaymentType
import com.woowahan.accountbook.navigation.BottomNavigationRoute
import com.woowahan.accountbook.navigation.Screen
import com.woowahan.accountbook.ui.theme.AccountBookTheme
import com.woowahan.accountbook.ui.theme.White80
import com.woowahan.accountbook.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccountBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val navList =
        listOf(
            BottomNavigationRoute.History,
            BottomNavigationRoute.Calendar,
            BottomNavigationRoute.Statistics,
            BottomNavigationRoute.Setting
        )
    val context = LocalContext.current

    viewModel.createTables()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation {
                navList.forEach { screen ->
                    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStackEntryAsState?.destination
                    BottomNavigationItem(
                        unselectedContentColor = White80,
                        selected = currentRoute?.hierarchy?.any { it.route == screen.route } == true,
                        label = { Text(screen.label) },
                        onClick = { navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        } },
                        icon = {
                            Icon(
                                painter = painterResource(screen.iconId),
                                contentDescription = screen.label
                            )
                        },
                    )
                }
            }
        }
    ) {
        Row(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavigationRoute.History.route
            ) {
                navigation(
                    route = BottomNavigationRoute.History.route,
                    startDestination = Screen.HistoryIndex.route
                ) {
                    composable(route = Screen.HistoryIndex.route) { HistoryScreen(navController, viewModel) }
                    composable(route = Screen.HistoryIndex.HistoryCreate.route) { HistoryCreateScreen(navController) }
                }
                navigation(
                    route = BottomNavigationRoute.Calendar.route,
                    startDestination = Screen.CalendarIndex.route
                ) {
                    composable(route = Screen.CalendarIndex.route) { CalendarScreen(viewModel) }
                }
                navigation(
                    route = BottomNavigationRoute.Statistics.route,
                    startDestination = Screen.StatisticsIndex.route
                ) {
                    composable(route = Screen.StatisticsIndex.route) { StatisticsScreen(viewModel) }
                }
                navigation(
                    route = BottomNavigationRoute.Setting.route,
                    startDestination = Screen.SettingIndex.route
                ) {
                    composable(route = Screen.SettingIndex.route) { SettingScreen(navController) }
                    composable(
                        route = "${Screen.SettingIndex.CategoryCreate.route}?settingMode={settingMode}?paymentType={paymentType}?id={id}",
                        arguments = listOf(
                            navArgument("settingMode") { this.type = NavType.StringType },
                            navArgument("paymentType") { this.type = NavType.StringType },
                            navArgument("id") { this.type = NavType.IntType },
                        ),
                    ) { navBackStackEntry ->
                        CategoryCreateScreen(
                            navBackStackEntry.arguments?.getString("settingMode") ?: SettingMode.Create.toString(),
                            navBackStackEntry.arguments?.getString("paymentType") ?: PaymentType.Nothing.toString(),
                            navBackStackEntry.arguments?.getInt("id") ?: 0
                        )
                    }
                    composable(
                        route = "${Screen.SettingIndex.PaymentMethodCreate.route}?settingMode={settingMode}?id={id}",
                        arguments = listOf(
                            navArgument("settingMode") { this.type = NavType.StringType },
                                    navArgument("id") { this.type = NavType.IntType },
                        )
                    ) { navBackStackEntry ->
                        PaymentMethodCreateScreen(
                            navBackStackEntry.arguments?.getString("settingMode") ?: SettingMode.Create.toString(),
                            navBackStackEntry.arguments?.getInt("id") ?: 0
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccountBookTheme {
        Main()
    }
}