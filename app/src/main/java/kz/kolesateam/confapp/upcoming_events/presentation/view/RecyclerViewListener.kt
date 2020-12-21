package kz.kolesateam.confapp.upcoming_events.presentation.view

interface RecyclerViewListener {
    fun onCurrentBrunchClick(position: Int, title: String, eventIdCurrent: Int)
    fun onNextBrunchClick(position: Int, title: String, eventIdNext: Int)
}
