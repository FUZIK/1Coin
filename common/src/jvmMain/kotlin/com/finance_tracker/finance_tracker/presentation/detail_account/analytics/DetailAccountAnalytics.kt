package com.finance_tracker.finance_tracker.presentation.detail_account.analytics

import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.Transaction

class DetailAccountAnalytics: com.finance_tracker.finance_tracker.core.analytics.BaseAnalytics() {

    override val screenName = "DetailAccountScreen"

    fun trackEditAccountClick(account: Account) {
        trackClick(
            eventName = "EditAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }

    fun trackTransactionClick(transaction: Transaction) {
        trackClick(
            eventName = "Transaction",
            properties = mapOf(
                "account_name" to transaction.account.name,
                "type" to transaction.type.analyticsName
            )
        )
    }

    fun trackIconClick(account: Account) {
        trackClick(
            eventName = "IconEditAccount",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }

    fun trackAddTransactionClick(account: Account) {
        trackClick(
            eventName = "AddTransaction",
            properties = mapOf(
                "name" to account.name,
                "type" to account.type.analyticsName,
            )
        )
    }
}