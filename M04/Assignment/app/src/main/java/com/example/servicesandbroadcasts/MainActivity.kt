package com.example.servicesandbroadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "IMAGEDOWNLOAD"
    }

    private lateinit var imageDownloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_download.setOnClickListener {
            val serviceIntent = Intent(this, ImageDownloadService::class.java)
            serviceIntent.putExtra(ImageDownloadService.BW, image_view.width)
            serviceIntent.putExtra(ImageDownloadService.BH, image_view.height)
            this.startService(serviceIntent)
        }

        imageDownloadReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.action == ImageDownloadService.FDA) {
                    val bitmap = intent.getParcelableExtra<Bitmap>(ImageDownloadService.DI)
                    image_view.setImageBitmap(bitmap)
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction(ImageDownloadService.FDA)
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(imageDownloadReceiver, intentFilter)
        Log.i(TAG, "receiver registered")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(imageDownloadReceiver)
        Log.i(TAG, "receiver unregistered")
    }
}
