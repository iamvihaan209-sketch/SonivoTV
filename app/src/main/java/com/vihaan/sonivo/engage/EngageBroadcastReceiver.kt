package com.vihaan.sonivo.engage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.engage.service.BroadcastReceiverPermissions
import com.google.android.engage.service.Intents

class EngageBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val publisher = EngagePublisher(context)
        when (intent.action) {
            Intents.ACTION_PUBLISH_RECOMMENDATION -> {
                publisher.publishImmediately(EngageConstants.PUBLISH_TYPE_RECOMMENDATION)
            }
            Intents.ACTION_PUBLISH_CONTINUATION -> {
                publisher.publishImmediately(EngageConstants.PUBLISH_TYPE_CONTINUATION)
            }
            Intents.ACTION_PUBLISH_FEATURED -> {
                publisher.publishImmediately(EngageConstants.PUBLISH_TYPE_FEATURED)
            }
        }
    }

    companion object {
        fun register(context: Context) {
            val receiver = EngageBroadcastReceiver()
            val appLifeContext = context.applicationContext
            
            appLifeContext.registerReceiver(
                receiver,
                IntentFilter(Intents.ACTION_PUBLISH_RECOMMENDATION),
                BroadcastReceiverPermissions.BROADCAST_REQUEST_DATA_PUBLISH_PERMISSION,
                null
            )
            appLifeContext.registerReceiver(
                receiver,
                IntentFilter(Intents.ACTION_PUBLISH_CONTINUATION),
                BroadcastReceiverPermissions.BROADCAST_REQUEST_DATA_PUBLISH_PERMISSION,
                null
            )
            appLifeContext.registerReceiver(
                receiver,
                IntentFilter(Intents.ACTION_PUBLISH_FEATURED),
                BroadcastReceiverPermissions.BROADCAST_REQUEST_DATA_PUBLISH_PERMISSION,
                null
            )
        }
    }
}
