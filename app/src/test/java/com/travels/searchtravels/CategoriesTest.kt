package com.travels.searchtravels

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.api.services.vision.v1.model.LatLng
import com.preview.planner.prefs.AppPreferences
import com.travels.searchtravels.activity.MainActivity
import com.travels.searchtravels.api.OnVisionApiListener
import com.travels.searchtravels.api.VisionApi
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CategoriesTest {

    val eventLatch = CountDownLatch(1)

    //@Test
    fun createActivity() {
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    private fun checkCategory(url: String, cat: String) {
        VisionApi.findLocation(
            url.getBitmapFromURL(),
            AppPreferences.getToken(getApplicationContext()),
            object : OnVisionApiListener {
                override fun onSuccess(latLng: LatLng) {
                    Assert.fail("you're trying not a category link")
                    eventLatch.countDown()
                }

                override fun onErrorPlace(category: String) {
                    Assert.assertEquals(category, cat)
                    eventLatch.countDown()
                }

                override fun onError() {
                    Assert.fail("connection problem: token or smth")
                    eventLatch.countDown()
                }
            })
        eventLatch.await(10000, TimeUnit.MILLISECONDS)
        shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun visionApiOceanCategoryTest() {
        checkCategory(
            "https://i.artfile.me/wallpaper/23-06-2014/2048x1382/priroda-morya-okeany-plyazh-839901.jpg",
            "ocean"
        )
    }

    @Test
    fun visionApiSeaCategoryTest() {
        checkCategory(
            "https://w-dog.ru/wallpapers/10/12/434755555995923/pejzazh-zakat-plyazh-pesok-more-bereg.jpg",
            "sea"
        )
    }

    @Test
    fun visionApiMountainsCategoryTest() {
        checkCategory(
            "https://terra-z.com/wp-content/uploads/2015/11/00117-533x400.jpg",
            "mountain"
        )
    }

    @Test
    fun visionApiBeachCategoryTest() {
        checkCategory(
            "https://img4.goodfon.ru/original/2560x1440/0/c4/tropiki-more-pliazh-kariby.jpg",
            "beach"
        )
    }

    @Test
    fun visionApiSnowCategoryTest() {
        checkCategory(
            "https://img2.goodfon.ru/original/2560x1600/7/49/quebec-canada-kvebek-kanada-les-reka-zima-sneg-derevia.jpg",
            "snow"
        )
    }

}