package kz.kolesateam.confapp.all_events.all_events_view

interface EventViewListener {
    fun onItemClick(
        position: Int,
        theme: String,
        eventId: Int
    )
}
