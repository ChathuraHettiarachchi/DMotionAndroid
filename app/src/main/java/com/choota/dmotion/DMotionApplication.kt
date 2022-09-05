package com.choota.dmotion

import android.app.Application
import com.rommansabbir.networkx.NetworkXLifecycle
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.SmartConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DMotionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkXProvider.enable(SmartConfig(this, true, NetworkXLifecycle.Application))
    }
}