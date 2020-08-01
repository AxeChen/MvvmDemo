package com.axe.libcommon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.SIM_STATE_READY
import com.axe.libcommon.ext.wifiManager
import java.util.*

/**
 * 获取设备的一些信息
 * 注意：不一定准确，可能安卓版本不同获取的方式也不一样，所以要特别注意！！！
 */
object DeviceUtil {

    private const val DEVICE_ID_KEY = "deviceId"

    private const val MAC_ADDRESS_KEY = "macAddress"

    private const val IMEI_KEY = "imei"

    private var deviceId by Preference(DEVICE_ID_KEY, "")

    private var mac: String by Preference(MAC_ADDRESS_KEY, "")

    private var imeiId: String by Preference(IMEI_KEY, "")


    @SuppressLint("HardwareIds")
    fun Context.deviceId(): String {
        if (deviceId.isEmpty()) {
            val androidId =
                Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            deviceId =
                if (androidId.isNotEmpty() && androidId.length > 4 && androidId != "android") {
                    androidId
                } else {
                    ""
                }
        }
        return deviceId
    }

    @SuppressLint("HardwareIds")
    fun Context.macAddress(): String {
        val wifiInfo = wifiManager?.connectionInfo
        wifiInfo?.run {
            if (mac.isEmpty()) {
                mac = try {
                    if (macAddress.isEmpty()) {
                        UUID.randomUUID().toString()
                    } else {
                        macAddress
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    UUID.randomUUID().toString()
                }
            }
        }
        return mac
    }

    fun getDevice(): String {
        return Build.DEVICE
    }

    fun getBrand(): String {
        return Build.BRAND
    }

    fun getModel(): String {
        return Build.MODEL
    }

    fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }

    fun getOSName(): String {
        return Build.VERSION.CODENAME
    }

    fun getSimOperator(context: Context): String {
        var returnSimOperator = ""
        val tm =
            context.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        tm?.apply {
            if (simState == SIM_STATE_READY) {
                val getSimOperator = simOperator
                if (!getSimOperator.isNullOrEmpty()) {
                    returnSimOperator = getSimOperator
                }
            }
        }
        return returnSimOperator
    }

    /**
     * 根据包名判断是否安装app
     */
    fun Context.isInstallApp(packageName: String): Boolean {
        packageManager.getInstalledPackages(0)
            .filter { it.packageName.equals(packageName, true) }
            .let { return it.isNotEmpty() }
    }
}