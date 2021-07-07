package com.lambdaschool.serviceimagedownloader

import android.app.Service
import android.content.Intent
import android.net.Network
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import java.lang.UnsupportedOperationException

// TODO: S04M04-1 create new service
class LargeImageDownloadService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("LargeImageDownload", "started")

        // TODO: S04M04-2 Override onStartCommand

        Thread(Runnable() {
            // TODO: S04M04-3 Add network call
            val bitmap = NetworkAdapter.getBitmapFromUrl("https://i.imgur.com/HaSmgGn.jpg")

            val intent = Intent(FILE_DOWNLOADED_ACTION).apply {
                putExtra(DOWNLOADED_IMAGE, bitmap)
            }

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            stopSelf()
        }).start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("LargeImageDownload", "created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LargeImageDownload", "destroyed")
    }


    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    companion object {
        const val FILE_DOWNLOADED_ACTION = "com.lambdaschool.serviceimagedownloader.FILE_DOWNLOADED"
        const val DOWNLOADED_IMAGE = "downloadedImage"
    }
}



// TODO: S04M04-3 Add network call