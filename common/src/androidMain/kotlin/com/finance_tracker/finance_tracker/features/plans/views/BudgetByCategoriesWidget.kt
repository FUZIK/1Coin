package com.finance_tracker.finance_tracker.features.plans.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.rememberAsyncImagePainter
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.BudgetCard
import com.finance_tracker.finance_tracker.domain.models.Budget
import dev.icerock.moko.resources.compose.stringResource

private const val MaxBudgetsBeforeUncoverWidget = 5

@Suppress("MagicNumber")
@Composable
internal fun BudgetByCategoriesWidget(
    modifier: Modifier = Modifier,
    budgets: List<Budget> = emptyList(),
) {

    var isRotated by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 180F else 0F,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutLinearInEasing
        )
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(MR.strings.budgets_by_categories),
                style = CoinTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = CoinTheme.color.content
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        //TODO
                    }
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = rememberAsyncImagePainter(MR.files.ic_plus),
                    contentDescription = null,
                    tint = CoinTheme.color.primary
                )
                Text(
                    text = stringResource(MR.strings.add_limit),
                    style = CoinTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    color = CoinTheme.color.primary
                )
            }
        }
        if (budgets.isEmpty()) {
            BudgetsEmptyStub(
                modifier = Modifier
                    .padding(top = 12.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = CoinTheme.color.dividers,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .`if`(budgets.size <= 5) {
                        padding(16.dp)
                    }
                    .`if`(budgets.size > 5) {
                        padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    }
                    .animateContentSize()
            ) {
                Column {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = if (expanded) {
                                budgets
                            } else {
                                budgets.take(MaxBudgetsBeforeUncoverWidget)
                            },
                            key = { it.category.id }
                        ) { budget ->
                            BudgetCard(budget)
                        }
                    }
                    if (budgets.size > MaxBudgetsBeforeUncoverWidget) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .clickable {
                                    expanded = !expanded
                                    isRotated = !isRotated
                                }
                                .align(Alignment.CenterHorizontally)
                                .rotate(rotationAngle),
                            painter = rememberAsyncImagePainter(MR.files.ic_arrow_down_plans),
                            contentDescription = null,
                            tint = CoinTheme.color.primary
                        )
                    }
                }
            }
        }
    }
}