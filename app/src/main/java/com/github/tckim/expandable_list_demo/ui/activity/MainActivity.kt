package com.github.tckim.expandable_list_demo.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.tckim.expandable_list_demo.R
import com.github.tckim.expandable_list_demo.databinding.ActivityMainBinding
import com.github.tckim.expandable_list_demo.ui.fragment.PartFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.textViewTitle.text = getString(R.string.app_name)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PartFragment())
                .commitNow()
        }
    }

}
