package com.example.guidebook.events

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guidebook.GuideApplication
import com.example.guidebook.R
import com.example.guidebook.cache.BitmapCache
import com.example.guidebook.images.ImageDownloader
import com.example.guidebook.models.guidebook.Data
import com.example.guidebook.request.GuideBookService
import com.example.guidebook.request.ImagesApi
import io.reactivex.disposables.Disposable

class UpcomingEventsFragment : Fragment(), UpcomingEventsContract.View {
    private lateinit var guidesPresenterImpl: UpcomingGuidesPresenterImpl
    private lateinit var upcomingEventsAdapter: UpcomingEventsAdapter
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.events, null)
        setRefreshLayout(view)
        setGuides(view.findViewById(R.id.events))
        initPresenters()
        requestUpcomingGuides()

        return view
    }

    private fun setRefreshLayout(view: View) {
        refreshLayout = view.findViewById(R.id.refresh)
        refreshLayout.isRefreshing = true
        refreshLayout.setOnRefreshListener { requestUpcomingGuides() }
        refreshLayout.setColorSchemeColors(ResourcesCompat.getColor(context!!.resources, R.color.colorPrimary, context!!.theme));
    }

    private fun setGuides(view: RecyclerView) {
        eventsRecyclerView = view;
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val guideApplication = context?.applicationContext as GuideApplication
        val imagesApi = guideApplication.retrofit.create(ImagesApi::class.java)

        upcomingEventsAdapter = UpcomingEventsAdapter(context!!, ImageDownloader(BitmapCache(), imagesApi))
        eventsRecyclerView.adapter = upcomingEventsAdapter
    }

    private fun initPresenters() {
        val guideApplication = context?.applicationContext as GuideApplication
        val guideBookService = guideApplication.retrofit.create(GuideBookService::class.java)

        guidesPresenterImpl = UpcomingGuidesPresenterImpl(guideBookService, this)
    }

    private fun requestUpcomingGuides() {
        disposable?.dispose()
        disposable = guidesPresenterImpl.getUpcomingEvents()
    }

    override fun onUpcomingGuidesUpdate(data: List<Data>?) {
        refreshLayout.isRefreshing = false
        data?.let {
            upcomingEventsAdapter.upcomingEvents = it
            upcomingEventsAdapter.notifyDataSetChanged()
        }
    }

    override fun onErrorLoadingGuides(error: Throwable) {
        refreshLayout.isRefreshing = false
        Snackbar.make(eventsRecyclerView, R.string.error_loading_events, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}