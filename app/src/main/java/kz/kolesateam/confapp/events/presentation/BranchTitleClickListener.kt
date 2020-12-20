package kz.kolesateam.confapp.events.presentation

import kz.kolesateam.confapp.events.data.models.BranchApiData

interface BranchTitleClickListener {

    fun onBranchTitleClick(
        branchData: BranchApiData
    ) {
    }
}