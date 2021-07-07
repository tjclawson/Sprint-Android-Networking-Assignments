package com.example.servicesandbroadcasts

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.lang.UnsupportedOperationException

class ImageDownloadService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "started")

        Thread(Runnable() {
            val width = intent?.getIntExtra(BW, 0) ?: 0
            val height = intent?.getIntExtra(BH, 0) ?: 0
            val bitmap = NetworkAdapter.getBitmapFromUrl("https://i.imgur.com/HaSmgGn.jpg", width, height)

            val intent = Intent(FDA).apply {
                putExtra(DI, bitmap)
            }

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            stopSelf()
        }).start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "destroyed")
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    companion object {
        const val FDA = "FILE_DOWNLOADED"
        const val DI = "DOWNLOADED_IMAGE"
        const val TAG = "IMAGEDOWNLOAD"
        const val BH = "BITMAP_HEIGHT"
        const val BW = "BITMAP_WIDTH"
    }
}