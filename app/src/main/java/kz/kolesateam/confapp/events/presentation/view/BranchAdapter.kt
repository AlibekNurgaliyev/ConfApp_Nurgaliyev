package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEvenetsListItem

class BranchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<UpcomingEvenetsListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> HeaderViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.header_layout, parent, false))
            else -> BranchViewHolder(View.inflate(parent.context, R.layout.branch_item, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.onBind(dataList[position].data as String)
        }
        if (holder is BranchViewHolder) {
            holder.onBind(dataList[position].data as BranchApiData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    fun setList(branchApiDataList: List<UpcomingEvenetsListItem>) {
        dataList.clear()
        dataList.addAll(branchApiDataList)
        notifyDataSetChanged()
    }
}