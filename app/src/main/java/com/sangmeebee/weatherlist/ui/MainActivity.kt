package com.sangmeebee.weatherlist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sangmeebee.weatherlist.R
import com.sangmeebee.weatherlist.cache.exceptions.DeleteCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.GetCacheWeatherException
import com.sangmeebee.weatherlist.cache.exceptions.PostCacheWeatherException
import com.sangmeebee.weatherlist.databinding.ActivityMainBinding
import com.sangmeebee.weatherlist.remote.exceptions.DisConnectNetworkException
import com.sangmeebee.weatherlist.remote.exceptions.EmptyResultLocationException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalAppTokenException
import com.sangmeebee.weatherlist.remote.exceptions.IllegalLocationException
import com.sangmeebee.weatherlist.util.WeatherListDividerDecoration
import com.sangmeebee.weatherlist.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val weatherAdapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setUpObserveUiState()
        setBackPressedDispatcher()
    }

    private fun initView() {
        setRecyclerView()
        setSwipeRefreshLayout()
    }

    private fun setRecyclerView() {
        binding.rvWeathers.apply {
            adapter = weatherAdapter
            addItemDecoration(WeatherListDividerDecoration(4))
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.apply {
            setOnRefreshListener {
                mainViewModel.fetchLoading(true)
            }
        }
    }

    private fun setUpObserveUiState() {
        observeError()
        observeWeathers()
        observeIsLoading()
    }

    private fun observeError() = repeatOnStarted {
        mainViewModel.uiState.map { it.error }.distinctUntilChanged().collectLatest { throwable ->
            if (throwable != null) {
                when (throwable) {
                    is DeleteCacheWeatherException -> showToast(resources.getString(R.string.main_delete_cache_error))
                    is PostCacheWeatherException -> showToast(resources.getString(R.string.main_post_cache_error))
                    is GetCacheWeatherException -> showToast(resources.getString(R.string.main_get_cache_error))
                    is IllegalLocationException -> showToast(resources.getString(R.string.main_illegal_location_error))
                    is EmptyResultLocationException -> showToast(resources.getString(R.string.main_empty_location_error))
                    is IllegalAppTokenException -> showToast(resources.getString(R.string.main_app_token_error))
                    is DisConnectNetworkException -> showToast(resources.getString(R.string.main_disconnect_network_error))
                }
                mainViewModel.fetchError(null)
            }
        }
    }

    private fun observeWeathers() = repeatOnStarted {
        mainViewModel.weathers.collectLatest { weathers ->
            weatherAdapter.submitList(weathers)
        }
    }

    private fun observeIsLoading() = repeatOnStarted {
        mainViewModel.uiState.map { it.isLoading }.distinctUntilChanged().collectLatest { isLoading ->
            binding.srlLoading.isRefreshing = isLoading
            if (isLoading) {
                mainViewModel.fetchWeather()
            }
        }
    }

    private fun showToast(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }
}
