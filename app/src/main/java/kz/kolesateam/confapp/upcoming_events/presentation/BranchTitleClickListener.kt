package kz.kolesateam.confapp.upcoming_events.presentation

import kz.kolesateam.confapp.upcoming_events.data.models.BranchApiData

interface BranchTitleClickListener {

    fun onBranchTitleClick(
        branchData: BranchApiData
    ) {
    }
}