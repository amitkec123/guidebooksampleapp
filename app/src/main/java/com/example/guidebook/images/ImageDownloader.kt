package com.example.guidebook.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.guidebook.cache.CacheInterface
import com.example.guidebook.request.ImagesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Provides the image from the queried url. It searches in memory cache for the url. If not found in cache, network
 * query is fired to retrieve the bitmap.
 */
class ImageDownloader(internal var cacheInterface: CacheInterface<Bitmap>, val imagesApi: ImagesApi) {

    interface DownloadListener {
        fun onDownload(item: DownloadedImage)
    }

    fun getBitmap(url: String, downloadListener: DownloadListener) {
        val cachedBitmap = cacheInterface.getFromCache(url)
        if (cachedBitmap != null) {
            downloadListener.onDownload(getDownloadedImage(url, cachedBitmap))
        } else {
            requestImageFromNetwork(url, downloadListener)
        }
    }

    private fun requestImageFromNetwork(url: String, downloadListener: DownloadListener) {
        imagesApi.getImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseBody -> parseResponse(responseBody, url, downloadListener) })
    }

    private fun parseResponse(responseBody: ResponseBody, url: String, downloadListener: DownloadListener) {
        val bitmap = BitmapFactory.decodeStream(responseBody.byteStream())
        cacheInterface.addToCache(url, bitmap)
        downloadListener.onDownload(getDownloadedImage(url, bitmap))
    }

    private fun getDownloadedImage(url: String, bitmap: Bitmap): DownloadedImage {
        return DownloadedImage(url, bitmap)
    }
}