package com.example.guidebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.guidebook.events.UpcomingEventsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.main_container, UpcomingEventsFragment())
            commit()
        }
    }
}