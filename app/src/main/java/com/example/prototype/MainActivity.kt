package com.example.prototype

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.prototype.view.CashierPresentation
import com.example.prototype.view.CustomerScreen
import com.example.prototype.view.ScreenWithControlBase
import com.example.prototype.viewmodels.SharedViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SharedViewModel
    private var secondScreen: CashierPresentation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

//        setContent {
//            CustomerScreen(viewModel)
//        }
        setContent {
            ScreenWithControlBase(viewModel = viewModel, isPrimary = true) {
                CustomerScreen(viewModel)
            }
        }

        showSecondDisplayIfAvailable()
    }

    private fun showSecondDisplayIfAvailable() {
        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays
        if (displays.size > 1) {
            val secondaryDisplay = displays[1]
            val parentRegistry = (this as SavedStateRegistryOwner).savedStateRegistry

            secondScreen = CashierPresentation(
                context = this,
                display = secondaryDisplay,
                parentViewModelStore = viewModelStore,
                parentSavedStateRegistry = parentRegistry
            )
            secondScreen?.show()
        }
    }

    override fun onDestroy() {
        secondScreen?.dismiss()
        super.onDestroy()
    }
}