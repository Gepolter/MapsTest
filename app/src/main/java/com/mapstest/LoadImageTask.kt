package com.mapstest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.IOException
import java.net.URL
//https://lindevs.com/load-image-from-url-using-asynctask-in-android

class LoadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg params: String?): Bitmap? {
        try {
            val stream = URL(urls[0]).openStream()

            return BitmapFactory.decodeStream(stream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null

    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }
}