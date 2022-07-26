package com.woowahan.accountbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.woowahan.accountbook.components.calendar.CalendarScreen
import com.woowahan.accountbook.components.history.HistoryScreen
import com.woowahan.accountbook.components.history.create.HistoryCreateScreen
import com.woowahan.accountbook.components.setting.SettingScreen
import com.woowahan.accountbook.components.setting.create.CategoryCreateScreen
import com.woowahan.accountbook.components.setting.create.PaymentMethodCreateScreen
import com.woowahan.accountbook.components.statistics.StatisticsScreen
import com.woowahan.accountbook.navigation.BottomNavigationRoute
import com.woowahan.accountbook.navigation.Screen
import com.woowahan.accountbook.ui.theme.AccountBookTheme
import com.woowahan.accountbook.ui.theme.White80

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
fun Main() {
    var currentRoute by rememberSaveable { mutableStateOf(BottomNavigationRoute.History.route) }
    val navController = rememberNavController()
    val navList =
        listOf(
            BottomNavigationRoute.History,
            BottomNavigationRoute.Calendar,
            BottomNavigationRoute.Statistics,
            BottomNavigationRoute.Setting
        )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation {
                navList.forEach {
                    BottomNavigationItem(
                        unselectedContentColor = White80,
                        selected = currentRoute == it.route,
                        label = { Text(it.label) },
                        onClick = {
                            currentRoute = it.route
                            navController.navigate(it.route)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(it.iconId),
                                contentDescription = it.label
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
                    composable(route = Screen.HistoryIndex.route) { HistoryScreen(navController) }
                    composable(route = Screen.HistoryIndex.HistoryCreate.route) { HistoryCreateScreen() }
                }
                navigation(
                    route = BottomNavigationRoute.Calendar.route,
                    startDestination = Screen.CalendarIndex.route
                ) {
                    composable(route = Screen.CalendarIndex.route) { CalendarScreen() }
                }
                navigation(
                    route = BottomNavigationRoute.Statistics.route,
                    startDestination = Screen.StatisticsIndex.route
                ) {
                    composable(route = Screen.StatisticsIndex.route) { StatisticsScreen() }
                }
                navigation(
                    route = BottomNavigationRoute.Setting.route,
                    startDestination = Screen.SettingIndex.route
                ) {
                    composable(route = Screen.SettingIndex.route) { SettingScreen() }
                    composable(route = Screen.SettingIndex.CategoryCreate.route) { CategoryCreateScreen() }
                    composable(route = Screen.SettingIndex.PaymentMethodCreate.route) { PaymentMethodCreateScreen() }
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