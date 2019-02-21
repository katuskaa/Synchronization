package diabetes.com.synchronization.screens.main.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import diabetes.com.synchronization.R
import diabetes.com.synchronization.screens.main.MainFragment

class IntervalAdapter(private val adapterHandler: IntervalAdapterHandler) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapterItems = mutableListOf<IntervalItem>()

    fun injectData(adapterItems: MutableList<IntervalItem>) {
        this.adapterItems.clear()
        this.adapterItems.addAll(adapterItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog__adapter_item, parent, false)
        return IntervalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder: IntervalViewHolder = holder as IntervalViewHolder

        val item = adapterItems[position]
        val context = holder.adapterItemTV.context

        holder.adapterItemTV.text = item.interval.itemName

        if (item.selected) {
            holder.adapterItemTV.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.adapterItemTV.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.adapterItemTV.setOnClickListener {
            item.selected = true
            holder.adapterItemTV.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            adapterHandler.onItemSelected(item)
        }
    }

    override fun getItemCount(): Int = this@IntervalAdapter.adapterItems.size

    data class IntervalItem(val interval: MainFragment.Interval, var selected: Boolean = false)

    interface IntervalAdapterHandler {
        fun onItemSelected(item: IntervalItem)
    }
}