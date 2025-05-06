package com.example.prototype.view

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.prototype.viewmodels.SharedViewModel

class CashierPresentation(
    context: Context,
    display: Display,
    private val parentViewModelStore: ViewModelStore,
    private val parentSavedStateRegistry: SavedStateRegistry
) : Presentation(context, display), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedStateRegistryController.performRestore(savedInstanceState)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        val composeView = ComposeView(context).apply {
            // Provide lifecycle, state, viewmodel support to Compose
            setViewTreeLifecycleOwner(this@CashierPresentation)
            setViewTreeViewModelStoreOwner(this@CashierPresentation)
            setViewTreeSavedStateRegistryOwner(this@CashierPresentation)

            setContent {
                // Inject your shared ViewModel
                val viewModel = ViewModelProvider(this@CashierPresentation)
                    .get(SharedViewModel::class.java)

                //CashierScreen(viewModel)
                ScreenWithControlBase(viewModel = viewModel, isPrimary = false) {
                    CashierScreen(viewModel)
                }
            }
        }

        setContentView(composeView)

        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onStop()
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry
    override val viewModelStore: ViewModelStore
        get() = parentViewModelStore
}


