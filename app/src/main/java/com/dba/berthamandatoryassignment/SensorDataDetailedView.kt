package com.dba.berthamandatoryassignment

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sensor_data_detailed_view.*


class SensorDataDetailedView : AppCompatActivity() {
        private var selectedUserDataObject: SensorUserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_data_detailed_view)

        val intent = intent
        selectedUserDataObject = intent.getSerializableExtra("object") as SensorUserData


        insertDataToTextViews(selectedUserDataObject)


    }

    fun insertDataToTextViews(userDataObject: SensorUserData?) {


        detailTitle!!.text = "Detailed data from ${userDataObject!!.dateFormatted}:"

        deviceId?.text = "${userDataObject.deviceId}"
        pm25?.text = "${userDataObject.pm25}"
        pm10?.text = "${userDataObject.pm10}"
        co2?.text = "${userDataObject.co2}"
        o3?.text = "${userDataObject.o3}"
        pressure?.text = "${userDataObject.pressure}"
        temperature?.text = "${userDataObject.temperature}"
        humidity?.text = "${userDataObject.humidity}"
        utc?.text = "${userDataObject.utc}"
        latitude?.text = "${userDataObject.latitude}"
        longitude?.text = "${userDataObject.longitude}"
        noise?.text = "${userDataObject.noise}"
        userId?.text = userDataObject.userId

    }
}
