package kz.kolesateam.confapp.upcoming_events.data.datasource

interface UserNameDataSource {
    fun getUserName(): String?
    fun saveUserName(
        userName: String
    )
}