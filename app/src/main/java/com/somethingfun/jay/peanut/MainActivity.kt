package com.somethingfun.jay.peanut

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.somethingfun.jay.peanut.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.samples.setOnClickListener {
            startActivity(Intent(this, GeoActivity::class.java))
        }

        binding.bezier.setOnClickListener {
            startActivity(Intent(this, BezierActivity::class.java))
        }

        binding.taegeukgi.setOnClickListener {
            startActivity(Intent(this, TaegeukgiActivity::class.java))
        }
    }
}