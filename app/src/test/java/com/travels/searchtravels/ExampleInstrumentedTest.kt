package com.travels.searchtravels

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.travels.searchtravels.activity.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *
 *
 * — которые позволят убедиться, что приложение правильно реагирует на следующие категории
 *   загруженных изображений: море, океан, пляж, горы, снег, прочее (3 балла за каждую категорию);
 * — которые проверят корректность получаемых через API данных о цене путешествия (4 балла);
 * — которые проверят корректность получаемых через API данных о распознанном изображении (3 балла).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun createActivity() {

        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }
}