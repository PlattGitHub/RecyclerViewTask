package com.example.recyclerviewtask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewtask.R

/**
 * Simple activity that hosts all other fragments.
 *
 * @author Alexander Gorin
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WeatherForecastFragment.newInstance()).commit()
        }
    }

}
