package com.finance_tracker.finance_tracker.presentation.welcome.di

import com.finance_tracker.finance_tracker.data.data_sources.GoogleAuthDataSource
import com.finance_tracker.finance_tracker.presentation.welcome.WelcomeViewModel
import com.finance_tracker.finance_tracker.presentation.welcome.analytics.WelcomeAnalytics
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val welcomeModule = module {
    factoryOf(::WelcomeViewModel)
    factoryOf(::WelcomeAnalytics)
    factoryOf(::GoogleAuthDataSource)
}