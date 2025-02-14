package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun BudgetsEmptyStub(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = CoinTheme.color.dividers,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                vertical = 28.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = rememberAsyncImagePainter(MR.files.ic_error),
            contentDescription = null,
            tint = CoinTheme.color.content.copy(alpha = 0.5f)
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = stringResource(MR.strings.budget_empty_stub),
            textAlign = TextAlign.Center,
            style = CoinTheme.typography.subtitle2,
            color = CoinTheme.color.content.copy(alpha = 0.5f)
        )
    }
}