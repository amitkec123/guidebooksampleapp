package com.example.guidebook.cache

/**
 * Provides the standard interface for different implementations of Caching mecanism.
 *
 * @param <T> Object to be stored in cache.
</T> */
interface CacheInterface<T> {
    /**
     * Adds the object to the cache.
     *
     * @param key   Unique key for the cache.
     * @param value Value to be store in cache.
     */
    fun addToCache(key: String, value: T)

    /**
     * Requests object from the Cache depending on the key. It should return null if cache doesn't contains the object.
     *
     * @param key Look up key for the Object
     * @return Value associated with the key in cache, null otherwise.
     */
    fun getFromCache(key: String): T?
}