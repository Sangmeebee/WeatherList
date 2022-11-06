package com.sangmeebee.weatherlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.weatherlist.databinding.ItemWeatherContentBinding
import com.sangmeebee.weatherlist.databinding.ItemWeatherTitleBinding
import com.sangmeebee.weatherlist.model.WeatherModel
import com.sangmeebee.weatherlist.model.WeatherViewType

class WeatherAdapter :
    ListAdapter<WeatherModel, RecyclerView.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == WeatherViewType.TITLE.ordinal) {
            WeatherTitleViewHolder(ItemWeatherTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            WeatherContentViewHolder(ItemWeatherContentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == WeatherViewType.TITLE.ordinal) {
            (holder as WeatherTitleViewHolder).bind(getItem(position) as WeatherModel.WeatherTitle)
        } else {
            (holder as WeatherContentViewHolder).bind(getItem(position) as WeatherModel.WeatherContent)
        }
    }


    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    class WeatherTitleViewHolder(private val binding: ItemWeatherTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherTitle: WeatherModel.WeatherTitle) {
            binding.title = weatherTitle.city
        }
    }

    class WeatherContentViewHolder(private val binding: ItemWeatherContentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherContent: WeatherModel.WeatherContent) {
            binding.weather = weatherContent
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(
        oldItem: WeatherModel,
        newItem: WeatherModel,
    ): Boolean = oldItem.type == newItem.type


    override fun areContentsTheSame(
        oldItem: WeatherModel,
        newItem: WeatherModel,
    ): Boolean = oldItem == newItem
}
