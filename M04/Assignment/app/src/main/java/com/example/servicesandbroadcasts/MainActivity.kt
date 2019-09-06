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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_layout.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "IMAGEDOWNLOAD"
    }

    private lateinit var imageDownloadReceiver: BroadcastReceiver
    val imageList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_download.setOnClickListener {
            val serviceIntent = Intent(this, ImageDownloadService::class.java)
            serviceIntent.putExtra(ImageDownloadService.BW, bitmap_list_view.width)
            serviceIntent.putExtra(ImageDownloadService.BH, 600)
            this.startService(serviceIntent)
        }

        imageDownloadReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.action == ImageDownloadService.FDA) {
                    val bitmap = intent.getParcelableExtra<Bitmap>(ImageDownloadService.DI)
                    imageList.add(bitmap)
                    bitmap_list_view.apply {
                        setHasFixedSize(false)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = ImageAdapter(imageList)
                    }

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

        unregisterReceiver(imageDownloadReceiver)
        Log.i(TAG, "receiver unregistered")
        super.onDestroy()
    }
}
