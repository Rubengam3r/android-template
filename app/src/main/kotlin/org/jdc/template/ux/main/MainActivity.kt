package org.jdc.template.ux.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.webservice.directory.DirectoryService
import org.jdc.template.model.webservice.directory.data.PersonsDta
import org.jdc.template.ui.theme.AppTheme
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets, including IME animations (OS statusbar and nav bar colors is handled by app)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen()

        setContent {
            val uiState = viewModel.uiState
            val theme by uiState.selectedAppThemeFlow.collectAsStateWithLifecycle()

            val darkTheme = when(theme?.displayThemeType) {
                DisplayThemeType.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                DisplayThemeType.LIGHT -> false
                DisplayThemeType.DARK -> true
                null -> isSystemInDarkTheme()
            }

            val dynamicTheme = when(theme?.dynamicTheme) {
                true -> true
                else -> false
            }

            AppTheme(darkTheme, dynamicTheme) {
                MainScreen()
            }
        }
        makeAPIRequest() //~~~~~ DO NOT WANT TO HAVE THIS HERE ~~~~~~~~~
    }

    //~~~~~~~~~~~~~MOVE TO OWN CLASS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private fun makeAPIRequest() {
        val api = Retrofit.Builder()
                .baseUrl(DirectoryService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DirectoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.directoryByFullURL(DirectoryService.FULL_URL)
                if (response.isSuccessful)
                {
                    processWebServiceResponse(response)
                }
                else {
            Timber.e("API Call FAILED [${response.errorBody()}]")
                }
            } catch (e: Exception) {
                Timber.tag("Main").e("Error: %s", e.message)
            }
        }

    }

    fun processWebServiceResponse(response: Response<PersonsDta>)
    {
        if (response.isSuccessful) {
            Timber.tag("Main").d("response: %s", response.isSuccessful)
            response.body()?.let {
                processData(it)
            }
        }
    }

    fun processData(personsDta: PersonsDta)
    {
        for (dtaResult in personsDta.individuals) {
            Timber.i("Results: %s", dtaResult.firstName)
        }
    }
}
