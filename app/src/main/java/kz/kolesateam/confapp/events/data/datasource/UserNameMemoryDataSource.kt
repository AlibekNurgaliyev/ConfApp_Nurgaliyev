package kz.kolesateam.confapp.events.data.datasource

class UserNameMemoryDataSource : UserNameDataSource {
    private var userName: String? = null

    override fun getUserName(): String? = userName

    override fun saveUserName(userName: String) {
        this.userName = userName
    }
}