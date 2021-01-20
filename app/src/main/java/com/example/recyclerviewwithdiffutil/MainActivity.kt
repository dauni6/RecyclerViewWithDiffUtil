package com.example.recyclerviewwithdiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerviewwithdiffutil.databinding.ActivityMainBinding

/**
 * 이 샘플은 https://deque.tistory.com/139 를 참고하여 만듦
 * */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tileAdapter = TileAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.mainRecyclerview.apply {
            adapter = tileAdapter
            layoutManager = GridLayoutManager(applicationContext, 5)
            setHasFixedSize(true)
        }

        binding.btnShuffle.setOnClickListener { tileAdapter.shuffle() }
        binding.btnErase.setOnClickListener { tileAdapter.eraseOneTile() }
        binding.btnThreeErase.setOnClickListener { tileAdapter.eraseThreeTile() }
        binding.btnAdd.setOnClickListener { tileAdapter.addOneTile() }
        binding.btnThreeAdd.setOnClickListener { tileAdapter.addThreeTile() }

    }

}
