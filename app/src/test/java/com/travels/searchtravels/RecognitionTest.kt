package com.travels.searchtravels

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.api.services.vision.v1.model.LatLng
import com.preview.planner.prefs.AppPreferences
import com.travels.searchtravels.activity.MainActivity
import com.travels.searchtravels.api.OnVisionApiListener
import com.travels.searchtravels.api.VisionApi
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@RunWith(AndroidJUnit4::class)
class RecognitionTest {

    val eventLatch = CountDownLatch(1)

    fun createActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    private fun checkCorrectPlace(url: String, lat: Double, lng: Double) {
        VisionApi.findLocation(
            url.getBitmapFromURL(),
            AppPreferences.getToken(ApplicationProvider.getApplicationContext()),
            object : OnVisionApiListener {
                override fun onSuccess(latLng: LatLng) {
                    val lat2 = latLng.latitude
                    val lng2 = latLng.longitude
                    var correct = true
                    if (abs(lat2 - lat) > 0.01) correct = false
                    if (abs(lng2 - lng) > 0.01) correct = false
                    Assert.assertTrue(correct)
                    eventLatch.countDown()
                }

                override fun onErrorPlace(category: String) {
                    Assert.fail("you're trying not a place link")
                    eventLatch.countDown()
                }

                override fun onError() {
                    Assert.fail("connection problem: token or smth")
                    eventLatch.countDown()
                }
            })
        eventLatch.await(10000, TimeUnit.MILLISECONDS)
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun visionApiSpecificPlaceIsEiffelTowerTest() {
        checkCorrectPlace(
            "www.destination360.com/europe/france/images/s/eiffel-tower.jpg",
            48.858239, 2.294585
        )
    }
}