package kz.kolesateam.confapp.events.data.datasource

interface UserNameDataSource {
    fun getUserName(): String?
    fun saveUserName(
        userName: String
    )
}