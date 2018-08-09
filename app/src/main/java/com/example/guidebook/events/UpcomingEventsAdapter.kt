package com.example.guidebook.events

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.guidebook.R
import com.example.guidebook.images.DownloadedImage
import com.example.guidebook.images.ImageDownloader
import com.example.guidebook.models.guidebook.Data

class UpcomingEventsAdapter(private val context: Context, private val imageDownloader: ImageDownloader) : RecyclerView.Adapter<UpcomingEventsAdapter.EventsViewHolder>() {
    internal var upcomingEvents: List<Data>? = null
    private val startDate: String
    private val endDate: String
    private val loadingDrawable: Drawable

    init {
        val resources = context.resources
        startDate = resources.getString(R.string.event_start)
        endDate = resources.getString(R.string.event_end)
        loadingDrawable = ColorDrawable(ResourcesCompat.getColor(resources, R.color.event_others_color, context.theme))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(View.inflate(context, R.layout.event_item, null))
    }

    override fun getItemCount(): Int {
        return upcomingEvents?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        upcomingEvents?.elementAt(position)?.also {
            viewHolder.name.text = it.name
            setDateOnView(viewHolder.startDate, it.startDate, startDate)
            setDateOnView(viewHolder.endDate, it.endDate, endDate)
            setEventImage(viewHolder.image, it.icon)
        }
    }

    private fun setDateOnView(view: TextView, date: String?, format: String) {
        if (date != null) {
            view.text = String.format(format, date)
        }
    }

    private fun setEventImage(imageView: ImageView, iconUrl: String?) {
        imageView.tag = iconUrl
        imageView.setImageDrawable(loadingDrawable)
        if (iconUrl != null) {
            imageDownloader.getBitmap(iconUrl, object : ImageDownloader.DownloadListener {
                override fun onDownload(item: DownloadedImage) {
                    if (item.url == imageView.tag) {
                        imageView.setImageBitmap(item.bitmap)
                    }
                }
            })
        }
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.eventImage)
        val name: TextView = itemView.findViewById(R.id.eventName)
        val startDate: TextView = itemView.findViewById(R.id.eventStart)
        val endDate: TextView = itemView.findViewById(R.id.eventEnd)
    }
}