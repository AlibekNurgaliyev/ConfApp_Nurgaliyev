package kz.kolesateam.confapp.upcoming_events.data.datasource

class BranchIdMemoryDataSource :BranchIdDataSource{
    private var branchId: String? = null

    override fun getBranchId(): String? = branchId

    override fun saveBranchId(branchId: String) {
        this.branchId = branchId
    }
}