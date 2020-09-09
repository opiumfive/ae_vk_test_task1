package com.travels.searchtravels

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.travels.searchtravels.activity.ChipActivity
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@RunWith(AndroidJUnit4::class)
class CostTest {

    @Test
    fun testParisPrice() {
        try {
            val obj =
                URL("https://autocomplete.travelpayouts.com/places2?locale=en&types[]=city&term=paris")
            val connection = obj.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0")
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
            connection.setRequestProperty("Content-Type", "application/json")
            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            bufferedReader.close()
            val responseJSON = JSONArray(response.toString())
            Log.d("myLogs", "responseJSON = $responseJSON")
            val code = responseJSON.getJSONObject(0).getString("code")
            val obj2 =
                URL("https://api.travelpayouts.com/v1/prices/cheap?origin=LED&depart_date=2019-12&return_date=2019-12&token=471ae7d420d82eb92428018ec458623b&destination=$code")
            val connection2 = obj2.openConnection() as HttpURLConnection
            connection2.requestMethod = "GET"
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0")
            connection2.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
            connection2.setRequestProperty("Content-Type", "application/json")
            val bufferedReader2 = BufferedReader(InputStreamReader(connection2.inputStream))
            var inputLine2: String?
            val response2 = StringBuffer()
            while (bufferedReader2.readLine().also { inputLine2 = it } != null) {
                response2.append(inputLine2)
            }
            bufferedReader2.close()
            val responseJSON2 = JSONObject(response2.toString())
            try {
                val ticketPrice = responseJSON2
                        .getJSONObject("data")
                        .getJSONObject(code)
                        .getJSONObject("1")
                        .getString("price").toInt()
                assertEquals(17000, ticketPrice)
            } catch (e: java.lang.Exception) {
                Assert.fail("Exception")
            }
        } catch (e: Exception) {
            Assert.fail("Exception")
        }
    }
}