package com.example.guidebook.events

import com.example.guidebook.models.guidebook.Data
import io.reactivex.disposables.Disposable

interface UpcomingEventsContract {
    interface View {
        fun onUpcomingGuidesUpdate(data: List<Data>?)
        fun onErrorLoadingGuides(error: Throwable)
    }

    interface UpcomingGuidesPresenter {
        fun getUpcomingEvents(): Disposable
    }
}