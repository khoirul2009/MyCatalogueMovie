package com.mymovie

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import com.mymovie.core.di.databaseModule
import com.mymovie.core.di.networkModule
import com.mymovie.core.di.repositoryModule
import com.mymovie.core.utils.NightMode
import com.mymovie.di.useCaseModule
import com.mymovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_auto)
        )?.apply {
            val mode = NightMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
        setAppLanguage()

    }

    fun setAppLanguage() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_lang),
            getString(R.string.pref_lang_en)
        ).apply {
            val locale = if(this == getString(R.string.pref_lang_id)) "id-ID" else "en-US"
            val appLocal:  LocaleListCompat = LocaleListCompat.forLanguageTags(locale)
            AppCompatDelegate.setApplicationLocales(appLocal)
        }
    }

}