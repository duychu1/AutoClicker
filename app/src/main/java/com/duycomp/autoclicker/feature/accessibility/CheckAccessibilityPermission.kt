package com.duycomp.autoclicker.feature.accessibility

import android.content.Context
import android.provider.Settings
import android.text.TextUtils

fun checkAccessibilityPermission(context: Context, accessibilityClass: Class<*>): Boolean {
    var accessibilityEnabled = 0
    val service: String = context.packageName
        .toString() + "/" + accessibilityClass.canonicalName
    try {
        accessibilityEnabled = Settings.Secure.getInt(
            context.applicationContext.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        )
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }
    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
    if (accessibilityEnabled == 1) {
        val settingValue: String = Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        mStringColonSplitter.setString(settingValue)
        while (mStringColonSplitter.hasNext()) {
            val accessibilityService = mStringColonSplitter.next()
            if (accessibilityService.equals(service, ignoreCase = true)) {
                return true
            }
        }
    } else {
        return false
    }
    return false
}