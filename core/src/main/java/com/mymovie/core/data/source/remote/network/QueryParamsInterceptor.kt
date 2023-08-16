package com.mymovie.core.data.source.remote.network




import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mymovie.core.R
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class QueryParamsInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val lang: String = getListPreferenceValue(context, context.getString(R.string.pref_key_lang), context.getString(R.string.pref_lang_en))

        val urlBuilder: HttpUrl.Builder = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", "d2405adf3f1294ee3227f943df0b09e4")
            .addQueryParameter("language", lang)

        val newRequest = originalRequest.newBuilder()
            .url(urlBuilder.build())
            .build()
        return chain.proceed(newRequest)
    }

    fun getListPreferenceValue(context: Context, key: String, defaultValue: String): String {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

}