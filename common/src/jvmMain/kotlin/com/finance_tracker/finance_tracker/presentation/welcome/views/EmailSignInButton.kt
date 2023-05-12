package com.finance_tracker.finance_tracker.presentation.welcome.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BaseButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun EmailSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseButton(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        borderColor = CoinTheme.color.primaryVariant,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(MR.strings.welcome_continue_email),
            style = CoinTheme.typography.body1_medium,
            color = LocalContentColor.current
        )
    }
}