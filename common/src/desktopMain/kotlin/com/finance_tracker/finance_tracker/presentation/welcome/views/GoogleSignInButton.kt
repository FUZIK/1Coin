package com.finance_tracker.finance_tracker.presentation.welcome.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("MissingModifierDefaultValue")
@Composable
internal actual fun GoogleSignInButton(
    onClick: () -> Unit,
    onSuccess: (token: String) -> Unit,
    onError: (exception: Exception) -> Unit,
    modifier: Modifier
) {
    /* ignore */
}