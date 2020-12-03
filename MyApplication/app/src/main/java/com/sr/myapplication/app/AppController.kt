package com.sr.myapplication.app

import android.app.Application
import com.sr.myapplication.di.AppComponent
import com.sr.myapplication.di.AppModule
import com.sr.myapplication.di.DaggerAppComponent

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent()
    }

    companion object {
        var appComponent: AppComponent? = null
            private set

        fun buildAppComponent(): AppComponent {
            return DaggerAppComponent.builder().appModule(AppModule()).build()
        }
    }
}