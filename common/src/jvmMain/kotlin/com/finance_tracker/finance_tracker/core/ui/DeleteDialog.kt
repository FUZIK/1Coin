package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.imePadding
import com.finance_tracker.finance_tracker.core.common.navigationBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun DeleteAlertDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    DialogSurface {
        DeleteDialog(
            titleEntity = titleEntity,
            onCancelClick = onCancelClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
internal fun DeleteBottomDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    DialogSurface {
        DeleteDialog(
            titleEntity = titleEntity,
            onCancelClick = onCancelClick,
            onDeleteClick = onDeleteClick,
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
        )
    }
}

@Composable
fun DialogSurface(
    content: @Composable () -> Unit
) {
    CoinTheme {
        Surface(
            color = CoinTheme.color.backgroundSurface
        ) {
            content()
        }
    }
}

@Composable
private fun DeleteDialog(
    titleEntity: String,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 16.dp),
            text = titleEntity,
            style = CoinTheme.typography.h4,
            color = CoinTheme.color.content
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            text = stringResource(MR.strings.dialog_body_delete),
            style = CoinTheme.typography.body1,
            color = CoinTheme.color.content
        )
        Row(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 8.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ActionButton(
                text = stringResource(MR.strings.btn_cancel),
                onClick = { onCancelClick.invoke() }
            )

            Spacer(modifier = Modifier.width(24.dp))

            ActionButton(
                text = stringResource(MR.strings.btn_delete),
                textColor = CoinTheme.color.accentRed,
                onClick = { onDeleteClick.invoke() }
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = CoinTheme.color.content,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick.invoke() }
            .padding(8.dp),
        text = text,
        style = CoinTheme.typography.body1_medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = textColor,
        textAlign = TextAlign.Center
    )
}