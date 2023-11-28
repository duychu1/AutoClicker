package com.duycomp.autoclicker.feature.overlay.di

import android.content.Context
import com.duycomp.autoclicker.feature.overlay.controller.controllerView
import com.duycomp.autoclicker.model.ViewLayout
import com.duycomp.autoclicker.feature.overlay.controller.controllerLayout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewLayoutModule {

    @Provides
    @Singleton
    fun providesControllerViewLayout(@ApplicationContext context: Context): ViewLayout {
        return ViewLayout(controllerView(context), controllerLayout())
    }

//    @Provides
//    @Singleton
//    fun providesTimerViewLayout(@ApplicationContext context: Context): ViewLayout {
//        TODO()
//    }
}