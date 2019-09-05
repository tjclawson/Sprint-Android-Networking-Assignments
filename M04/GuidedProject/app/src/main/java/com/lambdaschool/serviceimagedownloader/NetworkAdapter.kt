package com.lambdaschool.serviceimagedownloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkAdapter {
    val GET = "GET"
    val POST = "POST"
    val PUT = "PUT"
    val DELETE = "DELETE"
    val TIMEOUT = 3000

    // only handles get and post requests right now
    @JvmOverloads
    fun httpRequest(stringUrl: String, requestType: String, body: String = ""): String {
        var result = ""
        var stream: InputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(stringUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.readTimeout = TIMEOUT
            connection.connectTimeout = TIMEOUT
            connection.requestMethod = requestType

            if (requestType == GET || requestType == DELETE) {
                connection.connect()
            } else if (requestType == POST || requestType == PUT) {
                val outputStream = connection.outputStream
                outputStream.write(body.toByteArray())
                outputStream.close()
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = connection.inputStream
                if (stream != null) {
                    val reader = BufferedReader(InputStreamReader(stream))
                    val builder = StringBuilder()
                    var line = reader.readLine()
                    while (line != null) {
                        builder.append(line)
                        line = reader.readLine()
                    }
                    result = builder.toString()
                }
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            result = e.message ?: ""
        } catch (e: IOException) {
            e.printStackTrace()
            result = e.message ?: ""
        } finally {
            connection?.disconnect()

            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return result
    }

    fun getBitmapFromUrl(stringUrl: String): Bitmap? {
        var result: Bitmap? = null
        var stream: InputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(stringUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.readTimeout = TIMEOUT
            connection.connectTimeout = TIMEOUT

            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = connection.inputStream
                if (stream != null) {
                    result = BitmapFactory.decodeStream(stream)
                }
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()

            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return result
    }
}
