package com.ryannm.noteshero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ryannm.noteshero.presentation.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ListFragment::class.java, null)
            .commit()
    }
}