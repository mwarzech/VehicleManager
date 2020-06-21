package com.agh.wtm.vehiclemanager.fragments

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.agh.wtm.vehiclemanager.R
import kotlinx.android.synthetic.main.fragment_banner.view.*
import java.io.BufferedInputStream
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.util.concurrent.TimeUnit

class BannerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_banner, container, false)

        val imageViewBanner = view.findViewById<ImageView>(R.id.banner)

        if (imageViewBanner != null) {
            val image = DownloadImage()
                .execute("https://images.cdn4.stockunlimited.net/photos/man-in-coveralls-holding-fuel-pump-and-showing-ok-sign_1865754.png")
                .get(10, TimeUnit.SECONDS)
            imageViewBanner.setImageDrawable(image)
        }

        return view
    }


    private class DownloadImage: AsyncTask<String, Integer, Drawable>() {
        private fun downloadImage(_url: String): Drawable {
            return try {
                val url = URL(_url)
                val input: InputStream = url.openStream()
                val buf = BufferedInputStream(input)
                val bMap: Bitmap = BitmapFactory.decodeStream(buf)

                input?.close()
                buf.close()
                BitmapDrawable(Resources.getSystem(), bMap)
            } catch (e: Exception) {
                ColorDrawable(0)
            }
        }

        override fun doInBackground(vararg params: String?): Drawable {
            return downloadImage(params[0]!!)
        }
    }
}