package kz.kolesateam.confapp.upcoming_events.data.datasource

interface BranchIdDataSource {
    fun getBranchId(): String?
    fun saveBranchId(
        branchId: String
    )
}