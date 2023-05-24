package com.finance_tracker.finance_tracker.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.clicks.scaleClickAnimation
import com.finance_tracker.finance_tracker.core.common.formatters.AmountFormatMode
import com.finance_tracker.finance_tracker.core.common.formatters.format
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.common.toUIColor
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.domain.models.Account
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable
import kotlin.math.ceil
import kotlin.random.Random

val AccountCardHeight = 128.dp
val AccountCardWidth = 160.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AccountCard(
    data: Account,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(
                width = AccountCardWidth,
                height = AccountCardHeight
            )
            .scaleClickAnimation()
            .clip(RoundedCornerShape(12.dp))
            .background(data.colorModel.color.toUIColor())
            .noRippleClickable(onClick),
    ) {
        Column {
            Icon(
                painter = rememberAsyncImagePainter(data.icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                    .size(29.dp),
                tint = CoinTheme.color.white
            )
            Spacer(modifier = Modifier.weight(1f))

            val textStyleH = CoinTheme.typography.h5.fontSize.let {
                with(LocalDensity.current) {
                    it.toPx()
                }
            }
            Text(
                text = data.balance.format(mode = AmountFormatMode.NegativeSign),
                style = CoinTheme.typography.h5,
                color = CoinTheme.color.white,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        val squarePx = 5.dp.roundToPx()
                        val squareFloat = squarePx.toFloat()
                        val squareSize = Size(squareFloat, squareFloat)

                        val squareXOffset = ceil(size.width.div(squarePx) / 2)
                        val squareYOffset = ceil(textStyleH.div(squarePx) / 2)

                        val rows = ceil(size.width / squareFloat).toInt()
                        val columns = ceil(textStyleH / squareFloat).toInt()

                        drawContent()

                        drawRoundRect(
                            size = Size(
                                width = size.width,
                                height = textStyleH
                            ),
                            brush = SolidColor(Color.Black),
                            cornerRadius = CornerRadius(10f, 10f),
                        )

                        for (rI in 0 until rows) {
                            for (cI in 0 until columns) {
                                drawRect(Color.White,
                                    alpha = Random.nextDouble(0.7, 1.0).toFloat(),
                                    size = squareSize,
                                    topLeft = Offset(
                                        x =  rI * squarePx - squareXOffset,
                                        y =  cI * squarePx - squareYOffset),
                                    blendMode = BlendMode.SrcAtop
                                )
                            }
                        }
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val accountName = if (data.type == Account.Type.Cash) {
                "${data.name} (${data.balance.currency.symbol})"
            } else {
                data.name
            }
            Text(
                text = accountName,
                style = CoinTheme.typography.subtitle2,
                color = CoinTheme.color.white.copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}