package com.finance_tracker.finance_tracker.core.common.logger

import com.finance_tracker.finance_tracker.common.BuildConfig
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual class LoggerInitializer {

    actual fun init() {
        if (BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(false)
            Napier.base(DebugAntilog())
        } else {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog())
        }
    }
}