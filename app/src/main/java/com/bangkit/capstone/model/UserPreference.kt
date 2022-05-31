package com.bangkit.capstone.model

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

internal class UserPreference (context: Context) {

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USER_ID = "user_id"
        private const val NAME = "name"
        private const val GENDER = "gender"
        private const val TL = "tanggal_lahir"
        private const val TB = "tinggi_badan"
        private const val BB = "berat_badan"
        private const val TOKEN = "token"
    }


    @RequiresApi(Build.VERSION_CODES.M)
    val spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .build()
    @RequiresApi(Build.VERSION_CODES.M)
    val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()
    @RequiresApi(Build.VERSION_CODES.M)
    private var preferences: SharedPreferences = EncryptedSharedPreferences
        .create(
            context,
            "Session",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun setUser(user: UserModel){
        val editor = preferences.edit()
        editor.putString(USER_ID, user.userId)
        editor.putString(NAME, user.name)
        editor.putString(TL, user.tanggal_lahir)
        editor.putInt(GENDER, user.gender!!)
        editor.putInt(TB, user.tinggi_badan!!)
        editor.putInt(BB, user.berat_badan!!)
        editor.putString(TOKEN, user.token)
        editor.apply()
    }

    fun getUser(): UserModel {
        val user = UserModel()
        user.userId = preferences.getString(USER_ID, "")
        user.name = preferences.getString(NAME, "")
        user.tanggal_lahir = preferences.getString(TL, "")
        user.gender = preferences.getInt(GENDER, 3)
        user.tinggi_badan = preferences.getInt(TB, 0)
        user.berat_badan = preferences.getInt(BB, 0)
        user.token = preferences.getString(TOKEN, "")
        return user
    }

    fun logout(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

}