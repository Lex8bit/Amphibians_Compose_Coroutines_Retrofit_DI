package com.example.amphibians_compose_coroutines_di

import android.app.Application
import com.example.amphibians_compose_coroutines_di.data.AppContainer
import com.example.amphibians_compose_coroutines_di.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}