package com.example.guidebook.models.guidebook

data class Base(val total: String?, val data: List<Data>?)

data class Data(val startDate: String?, val objType: String?, val endDate: String?, val name: String?, val loginRequired: Boolean?, val url: String?, val venue: Venue?, val icon: String?) {
    class Venue {

    }
}