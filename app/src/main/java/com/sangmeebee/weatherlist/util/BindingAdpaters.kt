package com.sangmeebee.weatherlist.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sangmeebee.weatherlist.R
import com.sangmeebee.weatherlist.model.DAY_BROKEN_CLOUDS
import com.sangmeebee.weatherlist.model.DAY_CLEAR_SKY
import com.sangmeebee.weatherlist.model.DAY_FEW_CLOUDS
import com.sangmeebee.weatherlist.model.DAY_MIST
import com.sangmeebee.weatherlist.model.DAY_RAIN
import com.sangmeebee.weatherlist.model.DAY_SCATTERED_CLOUDS
import com.sangmeebee.weatherlist.model.DAY_SHOWER_RAIN
import com.sangmeebee.weatherlist.model.DAY_SNOW
import com.sangmeebee.weatherlist.model.DAY_THUNDER_STORM
import com.sangmeebee.weatherlist.model.NIGHT_BROKEN_CLOUDS
import com.sangmeebee.weatherlist.model.NIGHT_CLEAR_SKY
import com.sangmeebee.weatherlist.model.NIGHT_FEW_CLOUDS
import com.sangmeebee.weatherlist.model.NIGHT_MIST
import com.sangmeebee.weatherlist.model.NIGHT_RAIN
import com.sangmeebee.weatherlist.model.NIGHT_SCATTERED_CLOUDS
import com.sangmeebee.weatherlist.model.NIGHT_SHOWER_RAIN
import com.sangmeebee.weatherlist.model.NIGHT_SNOW
import com.sangmeebee.weatherlist.model.NIGHT_THUNDER_STORM

@BindingAdapter("iconType")
fun ImageView.setIconByType(type: String) {
    when (type) {
        DAY_CLEAR_SKY -> setImageResource(R.drawable.ic_clear_day)
        DAY_FEW_CLOUDS -> setImageResource(R.drawable.ic_few_clouds_day)
        DAY_SCATTERED_CLOUDS -> setImageResource(R.drawable.ic_scattered_cloud)
        DAY_BROKEN_CLOUDS -> setImageResource(R.drawable.ic_broken_cloud)
        DAY_SHOWER_RAIN -> setImageResource(R.drawable.ic_shower_rain)
        DAY_RAIN -> setImageResource(R.drawable.ic_rain_day)
        DAY_THUNDER_STORM -> setImageResource(R.drawable.ic_thunderstrom)
        DAY_SNOW -> setImageResource(R.drawable.ic_snow)
        DAY_MIST -> setImageResource(R.drawable.ic_mist)
        NIGHT_CLEAR_SKY -> setImageResource(R.drawable.ic_clear_night)
        NIGHT_FEW_CLOUDS -> setImageResource(R.drawable.ic_few_clouds_night)
        NIGHT_SCATTERED_CLOUDS -> setImageResource(R.drawable.ic_scattered_cloud)
        NIGHT_BROKEN_CLOUDS -> setImageResource(R.drawable.ic_broken_cloud)
        NIGHT_SHOWER_RAIN -> setImageResource(R.drawable.ic_shower_rain)
        NIGHT_RAIN -> setImageResource(R.drawable.ic_rain_night)
        NIGHT_THUNDER_STORM -> setImageResource(R.drawable.ic_thunderstrom)
        NIGHT_SNOW -> setImageResource(R.drawable.ic_snow)
        NIGHT_MIST -> setImageResource(R.drawable.ic_mist)
        else -> this.setImageResource(R.drawable.ic_scattered_cloud)
    }
}
