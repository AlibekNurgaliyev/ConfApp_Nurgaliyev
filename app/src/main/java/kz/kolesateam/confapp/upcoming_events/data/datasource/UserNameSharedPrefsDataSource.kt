package kz.kolesateam.confapp.upcoming_events.data.datasource

import android.content.SharedPreferences

private const val USER_NAME_KEY = "user_name"
private const val EMPTY_STRING = ""

class UserNameSharedPrefsDataSource(
    private val sharedPreferences: SharedPreferences
) : UserNameDataSource {

    override fun getUserName(): String? = sharedPreferences.getString(USER_NAME_KEY, EMPTY_STRING)

    override fun saveUserName(
        userName: String
    ) {
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
    }

}