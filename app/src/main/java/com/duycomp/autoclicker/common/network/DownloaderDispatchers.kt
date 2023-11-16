package com.duycomp.autoclicker.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val downloaderDispatchers: DownloaderDispatchers)

enum class DownloaderDispatchers {
    Default,
    IO,
}
