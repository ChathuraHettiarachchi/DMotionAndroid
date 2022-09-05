package com.choota.dmotion

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.rommansabbir.networkx.NetworkXLifecycle
import com.rommansabbir.networkx.NetworkXProvider
import com.rommansabbir.networkx.SmartConfig
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        NetworkXProvider.enable(SmartConfig(Application(), true, NetworkXLifecycle.Application))
    }
}