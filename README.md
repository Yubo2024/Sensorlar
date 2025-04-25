项目总结：Android Device Information, Network Status, and Sensor Listing
1.项目概述 
这个项目是一个Android应用程序，包含了三个主要功能模块：显示设备信息、检查网络状态以及列出设备传感器。每个功能通过独立的按钮进行触发，用户点击按钮时，应用会显示相应的信息。

2.主要功能 
- **Device Info (设备信息)
  显示当前设备的制造商、型号、Android版本等设备信息。
  
Network Status (网络状态检查)  
  检查设备是否连接到互联网，并显示相应的信息（如在线或离线）。

Sensor Listing (传感器列举)  
  列出设备上所有可用的传感器信息，包括传感器的名称和类型。
3.代码解析  
MainActivity.kt  
  这是应用的主Activity，它负责处理用户交互和调用功能：
  - 当应用启动时，`onCreate()` 方法初始化布局，并设置三个按钮的点击事件。
  - 每个按钮的点击事件都会调用相应的功能方法：
    - `btnDeviceInfo`: 显示设备信息。
    - `btnNetworkStatus`: 检查网络状态。
    - `btnSensors`: 列出设备的传感器。
  - 通过日志和 `Toast` 提示来展示功能的执行结果。

布局 (activity_main.xml) 
  这个布局文件定义了应用的UI。使用了 `LinearLayout` 布局，里面有三个按钮（每个按钮控制一个功能）和一个 `ScrollView`，其中显示相关信息。
  - 按钮：
    - `btnDeviceInfo`: 用于显示设备信息。
    - `btnNetworkStatus`: 用于检查网络状态。
    - `btnSensors`: 用于列出传感器。
  - `TextView` 显示信息，默认文字是“Click a button to display info”。
  - `ScrollView` 用于在文本内容较多时允许滚动显示。

4. 功能实现细节  
Device Info (设备信息)  
  使用 `android.os.Build` 类来获取设备的制造商、型号、设备ID、Android版本等信息。通过 `Log.d()` 方法在日志中显示，并使用 `Toast` 在UI中反馈给用户。

Network Status (网络状态)
  通过 `ConnectivityManager` 来检查设备的网络状态。`isInternetAvailable()` 方法根据设备的Android版本选择不同的方式来检查是否连接到互联网。
  - 如果Android版本为 `M` 或以上，使用 `NetworkCapabilities` 检查互联网连接。
  - 对于较旧的版本，直接检查 `activeNetworkInfo`。

Sensor Listing (传感器列举)  
  通过 `SensorManager` 获取所有传感器，并通过 `for` 循环遍历显示传感器名称和类型。

5. UI布局  
按钮 (Buttons) 
  每个按钮控制一个功能。按钮垂直排列，宽度为 `match_parent`，高度为 `wrap_content`，并使用间距来分隔。
  
滚动视图 (ScrollView)  
  用于显示可能会溢出屏幕的长文本，保持UI整洁。`TextView` 用于显示输出信息。
// Device Info Button Click
btnDeviceInfo.setOnClickListener {
    val deviceInfo = """
        Manufacturer: ${Build.MANUFACTURER}
        Model: ${Build.MODEL}
        Android Version: ${Build.VERSION.RELEASE}
        Device: ${Build.DEVICE}
    """.trimIndent()
    Log.d("DEVICE_INFO", deviceInfo)
    Toast.makeText(this, "Device Info displayed", Toast.LENGTH_SHORT).show()
}

// Network Status Button Click
btnNetworkStatus.setOnClickListener {
    if (isInternetAvailable()) {
        Log.d("NETWORK", "Internet available")
        Toast.makeText(this, "Internet available", Toast.LENGTH_SHORT).show()
    } else {
        Log.d("NETWORK", "No internet connection")
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}

// Sensor Button Click
btnSensors.setOnClickListener {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
    for (sensor in sensors) {
        Log.d("SENSOR", "${sensor.name} - Type: ${sensor.type}")
    }
    Toast.makeText(this, "Sensors listed in Log", Toast.LENGTH_SHORT).show()
}
7. 总结
这个项目展示了如何在Android应用中实现一些基础功能，如获取设备信息、检查网络状态以及列出设备的传感器。通过这种方式，用户可以更加了解自己的设备和网络连接状态。通过按钮触发功能，用户体验更直观，同时通过 `Toast` 和日志反馈给用户运行结果。
