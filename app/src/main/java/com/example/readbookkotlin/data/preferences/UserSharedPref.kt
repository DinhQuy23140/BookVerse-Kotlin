package com.example.readbookkotlin.data.preferences

import android.content.Context

class UserSharedPref(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(SharePrefConstants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveUserName(username: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_NAME, username).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_NAME, "")
    }

    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_EMAIL, email).apply()
    }

    fun getEmail():String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_EMAIL, "")
    }

    fun savePassword(password:String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_PASSWORD, password).apply()
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_PASSWORD, "")
    }

    fun setIsLogin(isLogin: Boolean) {
        sharedPreferences.edit().putBoolean(SharePrefConstants.KEY_IS_LOGIN, isLogin).apply()
    }

    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(SharePrefConstants.KEY_IS_LOGIN, false)
    }

    fun setSaveInf(isSaveInf: Boolean) {
        sharedPreferences.edit().putBoolean(SharePrefConstants.KEY_IS_SAVE_INF, isSaveInf).apply()
    }

    fun isSaveInf(): Boolean {
        return sharedPreferences.getBoolean(SharePrefConstants.KEY_IS_SAVE_INF, false)
    }


    fun saveImg(img: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_IMAGE, img).apply()
    }

    fun getImg(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_IMAGE, "")
    }

    fun savePhone(phone: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_PHONE, phone).apply()
    }

    fun getPhone(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_PHONE, "")
    }

    fun getDoB(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_DOB, "")
    }

    fun saveDoB(dob: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_DOB, dob).apply()
    }

    fun saveSex(sex: String) {
        sharedPreferences.edit().putString(SharePrefConstants.KEY_SEX, sex).apply()
    }

    fun getSex(): String? {
        return sharedPreferences.getString(SharePrefConstants.KEY_SEX, "")
    }
}