package com.yourcompany.android.moviediary.networking

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectivityChecker(private val connectivityManager: ConnectivityManager?) {

  fun hasNetworkConnection(): Boolean {
    val network = connectivityManager?.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
      || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
      || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
  }
}
