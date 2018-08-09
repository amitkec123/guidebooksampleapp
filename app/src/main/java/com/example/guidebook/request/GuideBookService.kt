package com.example.guidebook.request

import com.example.guidebook.models.guidebook.Base
import io.reactivex.Observable
import retrofit2.http.GET

interface GuideBookService {

    @GET("service/v2/upcomingGuides/")
    fun getUpcomingGuides(): Observable<Base>
}