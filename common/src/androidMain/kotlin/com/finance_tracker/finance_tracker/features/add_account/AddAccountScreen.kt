package com.finance_tracker.finance_tracker.features.add_account

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.LocalFixedInsets
import com.finance_tracker.finance_tracker.core.common.`if`
import com.finance_tracker.finance_tracker.core.common.toTextFieldValue
import com.finance_tracker.finance_tracker.core.common.toUiTextFieldValue
import com.finance_tracker.finance_tracker.core.common.view_models.watchViewActions
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.ComposeScreen
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.features.add_account.views.AccountColorTextField
import com.finance_tracker.finance_tracker.features.add_account.views.AccountNameTextField
import com.finance_tracker.finance_tracker.features.add_account.views.AccountTypeTextField
import com.finance_tracker.finance_tracker.features.add_account.views.AddAccountTopBar
import com.finance_tracker.finance_tracker.features.add_account.views.AmountTextField
import com.finance_tracker.finance_tracker.features.add_account.views.BalanceCalculationResult
import com.finance_tracker.finance_tracker.features.add_account.views.BalanceKeyboard
import com.finance_tracker.finance_tracker.features.add_account.views.EditAccountActions
import org.koin.core.parameter.parametersOf

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun AddAccountScreen(
    account: Account
) {
    ComposeScreen<AddAccountViewModel>(
        parameters = { parametersOf(account) }
    ) { viewModel ->
        val scaffoldState = rememberScaffoldState()
        val focusRequester = remember { FocusRequester() }
        val titleAccount by viewModel.enteredAccountName.collectAsState()
        val selectedType by viewModel.selectedType.collectAsState()
        val accountTypes by viewModel.types.collectAsState()
        val selectedColor by viewModel.selectedColor.collectAsState()
        val accountColors by viewModel.colors.collectAsState()
        val enteredBalance by viewModel.enteredBalance.collectAsState()
        val amountCurrencies by viewModel.amountCurrencies.collectAsState()
        val selectedCurrency by viewModel.selectedCurrency.collectAsState()
        val isAddButtonEnabled by viewModel.isAddButtonEnabled.collectAsState()
        val balanceCalculationResult by viewModel.balanceCalculationResult.collectAsState()
        var shouldShowAmountKeyboard by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        val screenDensity = LocalDensity.current
        var keyboardHeight by remember { mutableStateOf(0.dp) }
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            if (account == Account.EMPTY) {
                focusRequester.requestFocus()
            }
        }

        viewModel.watchViewActions { action, baseLocalsStorage ->
            handleAction(
                action = action,
                baseLocalsStorage = baseLocalsStorage,
                scaffoldState = scaffoldState,
                onCancelDeletingClick = viewModel::onCancelDeletingClick,
                onConfirmDeletingClick = viewModel::onConfirmDeletingClick
            )
        }

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { snackbarHostState: SnackbarHostState ->
                SnackbarHost(snackbarHostState) {
                    Snackbar(
                        snackbarData = it,
                        contentColor = CoinTheme.color.white,
                        actionColor = CoinTheme.color.primary,
                        modifier = Modifier.padding(bottom = LocalFixedInsets.current.navigationBarsHeight),
                        elevation = 0.dp
                    )
                }
            },
            topBar = {
                AddAccountTopBar(
                    topBarTextId = if (account == Account.EMPTY) {
                        MR.strings.new_account_title
                    } else {
                        MR.strings.accounts_screen_top_bar
                    },
                    onBackClick = viewModel::onBackClick
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .verticalScroll(scrollState)
                    .align(Alignment.TopCenter)
                    .imePadding()
                    .`if`(shouldShowAmountKeyboard) { padding(bottom = keyboardHeight) }) {
                    AccountNameTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        titleAccount = titleAccount,
                        onAccountNameChange = viewModel::onAccountNameChange,
                    )

                    Row(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                    ) {
                        AccountTypeTextField(
                            valueTextId = selectedType?.textId,
                            accountTypes = accountTypes,
                            onAccountTypeSelect = viewModel::onAccountTypeSelect,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AccountColorTextField(
                            selectedColor = selectedColor,
                            accountColors = accountColors,
                            onAccountColorSelect = viewModel::onAccountColorSelect,
                        )
                    }

                    AmountTextField(
                        enteredBalance = enteredBalance.toUiTextFieldValue(),
                        amountCurrencies = amountCurrencies,
                        selectedCurrency = selectedCurrency,
                        onAmountChange = { uiTextFieldValue: TextFieldValue ->
                            viewModel.onAmountChange(uiTextFieldValue.toTextFieldValue())
                        },
                        onCurrencySelect = viewModel::onCurrencySelect,
                        modifier = Modifier.onFocusChanged { shouldShowAmountKeyboard = it.isFocused },
                        isError = balanceCalculationResult.isError
                    )

                    BalanceCalculationResult(balanceCalculationResult.calculationResult)

                    EditAccountActions(
                        deleteEnabled = account != Account.EMPTY,
                        addEnabled = isAddButtonEnabled,
                        onDeleteClick = {
                            focusManager.clearFocus()
                            viewModel.onDeleteClick()
                        },
                        onAddAccountClick = viewModel::onAddAccountClick,
                    )
                }

                BalanceKeyboard(
                    shouldShowAmountKeyboard = shouldShowAmountKeyboard,
                    onKeyboardClick = viewModel::onKeyboardButtonClick,
                    onKeyboardClose = focusManager::clearFocus,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned {
                            with(screenDensity) {
                                keyboardHeight = it.size.height.toDp()
                            }
                        }
                )
            }
        }
    }
}
