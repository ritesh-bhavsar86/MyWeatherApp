package com.example.myassignment

import android.util.Log

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
        fun <T> network_error(msg: String, data: T?): Resource<T> {
            return Resource(Status.NETWORK_ERROR, data, msg)
        }
        fun <T> session_expiry(msg: String, data: T?): Resource<T> {
            return Resource(Status.SESSION_EXPIRY, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}