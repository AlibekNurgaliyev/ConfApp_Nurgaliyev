package kz.kolesateam.confapp.events.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.allEventsView.EventViewHolder

class AllEventsScreenAdapter : RecyclerView.Adapter<EventViewHolder>() {
    private val eventDataList: MutableList<EventApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            view = View.inflate(parent.context, R.layout.all_events_item, null)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventDataList[position])

    }

    override fun getItemCount(): Int = eventDataList.size

    fun setList(
        eventDataList: List<EventApiData>
    ) {
        this.eventDataList.clear()
        this.eventDataList.addAll(eventDataList)
        notifyDataSetChanged()
    }
}