package kz.kolesateam.confapp.events.data.datasource

import android.content.SharedPreferences

private const val USER_NAME_KEY = "user_name"
private const val EMPTY_STRING = ""

class UserNameSharedPrefsDataSource(
    private val sharedPrefernces: SharedPreferences
) : UserNameDataSource {

    override fun getUserName(): String? = sharedPrefernces.getString(USER_NAME_KEY, EMPTY_STRING)

    override fun saveUserName(
        userName: String
    ) {
        sharedPrefernces.edit().putString(USER_NAME_KEY, userName).apply()
    }

}