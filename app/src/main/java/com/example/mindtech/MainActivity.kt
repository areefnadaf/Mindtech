package com.example.mindtech

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.mindtech.adapters.CustomAdapter
import com.example.mindtech.adapters.ViewPagerAdapter
import com.example.mindtech.databinding.ActivityMainBinding
import com.example.mindtech.datamodels.Movie
import com.example.mindtech.viewmodels.MoviesViewModel
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var showContent = false
    private lateinit var mainViewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashDelay()

        mainViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        observers()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        mainViewModel.getMovieCategories()
        mainViewModel.getMovies(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
                binding.search.setText("")
                mainViewModel.getMovies(position)
            }
        })


        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println(" Text Before")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(" Text onChange")
                (binding.recyclerView.adapter as CustomAdapter).filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                println(" Text After")
                (binding.recyclerView.adapter as CustomAdapter).filter.filter(s)
            }

        })


    }

    fun observers() {
        mainViewModel.catmutableLiveData.observe(this, object : Observer<List<String>> {
            override fun onChanged(t: List<String>?) {
                println("Onchanged")
                binding.viewPager.adapter = ViewPagerAdapter(t)
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    //Some implementation
                }.attach()

            }

        })
        mainViewModel.mutableLiveData.observe(this, object : Observer<List<Movie>> {
            override fun onChanged(t: List<Movie>?) {
                println("Onchanged")
                binding.recyclerView.adapter = CustomAdapter(t)

            }

        })
    }


    fun splashDelay() {
        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (showContent) {
                        // The content is ready; start drawing.
                        println("COntent is ready")
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        println("COntent not ready")
                        Handler(Looper.getMainLooper()).postDelayed(
                            Runnable { showContent = true },
                            2000
                        )
                        false
                    }
                }
            }
        )

    }


}
