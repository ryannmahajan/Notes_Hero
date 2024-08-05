package com.ryannm.noteshero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ryannm.noteshero.domain.SharedPref
import com.ryannm.noteshero.presentation.list.ListFragment
import com.ryannm.noteshero.presentation.sign_in.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = if (SharedPref.getInstance().isLoggedIn)
            ListFragment::class.java else
                SignInFragment::class.java

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .commit()
    }
}