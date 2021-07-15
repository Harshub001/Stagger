package com.task.stagger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.task.stagger.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Stagger"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = Client.getClient().getPhotos()

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.VISIBLE
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val url = ArrayList<String>()
                        responseBody?.forEach {
                            url.add(it.urls.regular)
                        }
                        val adapter = MainActivityAdapter(url, this@MainActivity)
                        val recyclerView = binding.recyclerview
                        val layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        layoutManager.gapStrategy =
                            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = adapter
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: IOException) {
                e.message?.let { Log.d("ERROR IO", it) }
            } catch (e: Throwable) {
                e.message?.let { Log.d("ERROR Throwable", it) }
            }
        }
    }

}