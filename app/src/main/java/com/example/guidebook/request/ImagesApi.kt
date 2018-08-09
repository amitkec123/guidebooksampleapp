package com.example.guidebook.request

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ImagesApi {
    @GET
    fun getImage(@Url url: String): Observable<ResponseBody>
}
