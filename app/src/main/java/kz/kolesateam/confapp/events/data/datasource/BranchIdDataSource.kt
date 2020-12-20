package kz.kolesateam.confapp.events.data.datasource

interface BranchIdDataSource {
    fun getBranchId(): String?
    fun saveBranchId(
        branchId: String
    )
}