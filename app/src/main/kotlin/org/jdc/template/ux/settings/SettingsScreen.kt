@file:OptIn(ExperimentalMaterialApi::class)

package org.jdc.template.ux.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.dialog.InputDialog
import org.jdc.template.ui.compose.dialog.RadioDialog
import org.jdc.template.ui.compose.setting.Setting
import org.jdc.template.ui.navigation.WorkManagerStatusRoute

@Composable
fun SettingsScreen() {
    val navController = LocalNavController.current
    val viewModel: SettingsViewModel = viewModel()

    Column {
        Setting.Header(stringResource(R.string.display))
        Setting.Clickable(stringResource(R.string.theme), viewModel.currentThemeTitleFlow) { viewModel.onThemeSettingClicked() }
        Setting.Switch(stringResource(R.string.sort_by_last_name), viewModel.sortByLastNameFlow, false) { viewModel.setSortByLastName(it) }

        // not translated because this should not be visible for release builds
        Setting.Header("Developer Options")
        Setting.Clickable(text = "Work Manager Status", secondaryText = "Show status of all background workers") {
            navController?.navigate(WorkManagerStatusRoute.createRoute())
        }
        Setting.Clickable(text = "Last Installed Version Code", viewModel.currentLastInstalledVersionCodeFlow) { viewModel.onLastInstalledVersionCodeClicked() }
    }

    // Dialogs
    val themeRadioDialogData by viewModel.themeRadioDialogDataFlow.collectAsState()
    if (themeRadioDialogData.visible) {
        RadioDialog(
            title = themeRadioDialogData.title,
            items = themeRadioDialogData.items,
            onItemSelected = { viewModel.setTheme(it) },
            onDismissButtonClicked = { viewModel.dismissThemeDialog() }
        )
    }

    val lastInstalledVersionCodeDialogData by viewModel.lastInstalledVersionCodeDialogDataFlow.collectAsState()
    if (lastInstalledVersionCodeDialogData.visible) {
        InputDialog(
            title = lastInstalledVersionCodeDialogData.title,
            initialTextFieldText = lastInstalledVersionCodeDialogData.initialTextFieldText,
            onConfirmButtonClicked = { text -> viewModel.setLastInstalledVersionCode(text) },
            onDismissButtonClicked = { viewModel.dismissSetLastInstalledVersionCodeDialog() },
            minLength = 1,
            maxLength = 20
        )
    }
}



