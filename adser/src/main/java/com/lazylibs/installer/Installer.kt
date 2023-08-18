package com.lazylibs.installer

import android.text.TextUtils
import java.util.Locale

object Installer {

    fun toCheck(){

    }

    private const val f = "u"
    private const val o = "tm_c"
    fun isOrganic(str: String): Boolean {
        if (!TextUtils.isEmpty(str)) {// utm_campaign
            val lowerCase = str.lowercase(Locale.getDefault())
            if (lowerCase.contains(f + o + "ampaign")) {
                return false
            }
        }
        return true
    }
}