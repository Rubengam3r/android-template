package org.jdc.template.ux.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.theme.AppTheme

@AndroidEntryPoint
class WorkManagerMonitorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CompositionLocalProvider(LocalNavController provides findNavController()) {
                    val navController = LocalNavController.current
                    AppTheme {
                        WorkManagerStatusScreen(onBack = { navController?.popBackStack() })
                    }
                }
            }
        }
    }
}