package fi.lauriari.traveljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import fi.lauriari.traveljournal.navigation.InitNavigation
import fi.lauriari.traveljournal.ui.theme.TravelJournalTheme
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import fi.lauriari.traveljournal.viewmodels.LoginViewModel
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val loginViewModel: LoginViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelJournalTheme {
                navController = rememberNavController()
                InitNavigation(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    profileViewModel = profileViewModel,
                    groupViewModel = groupViewModel
                )
            }
        }
    }
}
