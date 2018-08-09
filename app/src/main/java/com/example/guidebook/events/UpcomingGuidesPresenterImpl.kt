package com.example.guidebook.events

import com.example.guidebook.request.GuideBookService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingGuidesPresenterImpl(private val guideBookService: GuideBookService, private val viewContract: UpcomingEventsContract.View) : UpcomingEventsContract.UpcomingGuidesPresenter {

    override fun getUpcomingEvents() {
        guideBookService.getUpcomingGuides()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewContract.onUpcomingGuidesUpdate(it.data) }, { viewContract.onErrorLoadingGuides(it) })
    }
}