package fi.lauriari.traveljournal.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import fi.lauriari.traveljournal.util.Constants.KEY_TOKEN
import fi.lauriari.traveljournal.util.Constants.KEY_USERNAME

object User {
    private fun preferences(context: Context): SharedPreferences {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences
    }

    fun getToken(context: Context): String? {
        return preferences(context).getString(KEY_TOKEN, null)
    }

    fun setToken(context: Context, token: String) {
        preferences(context).edit().apply {
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    fun removeToken(context: Context) {
        preferences(context).edit().apply {
            remove(KEY_TOKEN)
            apply()
        }
    }

    fun getUsername(context: Context): String? {
        return preferences(context).getString(KEY_USERNAME, null)
    }

    fun setUsername(context: Context, username: String) {
        preferences(context).edit().apply {
            putString(KEY_USERNAME, username)
            apply()
        }
    }

    fun removeUsername(context: Context) {
        preferences(context).edit().apply {
            remove(KEY_USERNAME)
            apply()
        }
    }
}