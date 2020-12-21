package kz.kolesateam.confapp.event_details

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.event_details.presentation.DetailsEventActivity

const val EVENT_ID = "EVENT_ID"

class EventDetailsRouter {
    fun createIntent(
        context: Context,
        branchId: Int
    ) = Intent(context, DetailsEventActivity::class.java).apply {
        putExtra(EVENT_ID, branchId)
    }
}