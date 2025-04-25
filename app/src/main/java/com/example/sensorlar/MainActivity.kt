package com.example.sensorlar

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var infoTextView: TextView
    private lateinit var btnDeviceInfo: Button
    private lateinit var btnNetworkStatus: Button
    private lateinit var btnSensors: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoTextView = findViewById(R.id.infoTextView)
        btnDeviceInfo = findViewById(R.id.btnDeviceInfo)
        btnNetworkStatus = findViewById(R.id.btnNetworkStatus)
        btnSensors = findViewById(R.id.btnSensors)

        btnDeviceInfo.setOnClickListener {
            infoTextView.text = getDeviceInfo()
        }

        btnNetworkStatus.setOnClickListener {
            val status = getNetworkStatus()
            infoTextView.text = status
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        btnSensors.setOnClickListener {
            infoTextView.text = getSensorInfo()
        }
    }

    private fun getDeviceInfo(): String {
        return """
            Manufacturer: ${Build.MANUFACTURER}
            Model: ${Build.MODEL}
            Android Version: ${Build.VERSION.RELEASE}
            Device Name: ${Build.DEVICE}
        """.trimIndent()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun getNetworkStatus(): String {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return "Not Internet"
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Not Internet"
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
        return if (isConnected) "Internet is work" else "Not Internet"
    }

    private fun getSensorInfo(): String {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensorInfo = StringBuilder("======== SENSOR LIST ========\n")
        for (sensor in sensors) {
            sensorInfo.append("- ${sensor.name} (Type: ${sensor.type})\n")
        }
        return sensorInfo.toString()
    }
}
