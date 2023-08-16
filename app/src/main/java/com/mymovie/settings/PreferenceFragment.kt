package com.mymovie.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mymovie.R

class PreferenceFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
        val themePreference: ListPreference? = findPreference(getString(R.string.pref_key_dark))
        themePreference?.onPreferenceChangeListener = this

        val languagePreference: ListPreference? = findPreference(getString(R.string.pref_key_lang))
        languagePreference?.setOnPreferenceChangeListener { _, newValue ->

            val selectedLanguage = newValue as String
            val locale: String = if(selectedLanguage == getString(R.string.pref_lang_id)) {
                "id-ID"
            } else {
                "en-US"
            }
            val appLocal: LocaleListCompat = LocaleListCompat.forLanguageTags(locale)
            AppCompatDelegate.setApplicationLocales(appLocal)
            activity?.recreate()
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }


    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        if (preference.key == getString(R.string.pref_key_dark)) {
            val nightMode = when (newValue.toString()) {
                "off" -> AppCompatDelegate.MODE_NIGHT_NO
                "on" -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            updateTheme(nightMode)
        }
        return true
    }


}