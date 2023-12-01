package com.duycomp.autoclicker.model

data class HmsMs(
    val h: Int,
    val m: Int,
    val s: Int,
    val ms: Int,
) {
    fun toLongMs(): Long {
        return ((h*3600 + m*60 + s)*1000 + ms).toLong()
    }

    fun toHHmmssSSS():String {
        return String.format("%02d:%02d:%02d.%03d", h,m,s,ms)
    }
}
fun longToHms(timeMilis: Long): HmsMs {
    var timeMs = timeMilis
    if (timeMs < 0) timeMs += 24*3600*1000
    val msParts = timeMs.toInt()%1000
    val timeSeconds = timeMs.toInt()/1000
    return HmsMs(
        h = timeSeconds/3600,
        m = timeSeconds%3600/60,
        s = timeSeconds%60,
        ms = msParts
    )
}
fun longToHHmmssSSS(milis: Long): String {
    return longToHms(milis).toHHmmssSSS()
}
