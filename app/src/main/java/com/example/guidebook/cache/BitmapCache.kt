package com.example.guidebook.cache

import android.graphics.Bitmap
import android.support.annotation.VisibleForTesting
import android.support.v4.util.LruCache

/**
 * Cache providing Bitmap storing facility.
 */
class BitmapCache : CacheInterface<Bitmap> {
    val cacheSize = getCacheSize()

    private val bmpCache: LruCache<String, Bitmap>

    init {
        bmpCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    override fun addToCache(key: String, bitmap: Bitmap) {
        bmpCache.put(key, bitmap)
    }

    /**
     * {@inheritDoc}
     */
    override fun getFromCache(key: String): Bitmap? {
        return bmpCache.get(key)
    }

    /**
     * Provides the size of the cache. Defaults to 1/8th of the app memory.
     *
     * @return
     */
    @VisibleForTesting
    internal fun getCacheSize(): Int {
        // 1/4th of App memory
        return (Runtime.getRuntime().maxMemory() / (1024 * 8)).toInt()
    }
}