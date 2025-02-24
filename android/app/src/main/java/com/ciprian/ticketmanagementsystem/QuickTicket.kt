package com.ciprian.ticketmanagementsystem

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuickTicket : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
