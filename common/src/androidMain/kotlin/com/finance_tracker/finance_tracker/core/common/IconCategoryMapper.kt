package com.finance_tracker.finance_tracker.core.common

import com.finance_tracker.finance_tracker.MR
import dev.icerock.moko.resources.FileResource

private val appContext: Context by lazy { getKoin().get() }

private val categories by lazy {
    List(63) {
        val index = it + 1
        val iconName = "ic_category_$index"
        iconName to MR.files.getCategoryIconFile(
            context = appContext, iconName
        )
    }
}
private val categoriesToFileResources = categories.associate { it }
private val fileResourcesToCategories = categories.associate { (categoryId, fileResource) ->
    fileResource to categoryId
}

actual fun String.toCategoryFileResource(): FileResource {
    return categoriesToFileResources[this] ?: error(
        "No category file resource for string '$this' in map $categoriesToFileResources"
    )
}

actual fun FileResource.toCategoryString(): String {
    return fileResourcesToCategories.mapKeys { it.key?.rawResId }[rawResId] ?: error(
        "No string for category FileResource's rawResId: '$rawResId' in map $fileResourcesToCategories"
    )
}