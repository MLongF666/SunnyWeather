package com.mlf.sunnyweather.ui.place

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlf.sunnyweather.MainActivity
import com.mlf.sunnyweather.R
import com.mlf.sunnyweather.databinding.PlaceFragmentBinding
import com.mlf.sunnyweather.logic.model.Place
import com.mlf.sunnyweather.ui.weather.WeatherActivity
import com.mlf.wanandroid.base.CommonAdapter

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/9 12:32
 * @version: 1.0
 */
class PlaceFragment: Fragment() {
    private lateinit var binding: PlaceFragmentBinding
    private var adapter: PlaceAdapter?= null
    private val viewModel by lazy { ViewModelProvider(this)[PlaceViewModel::class.java] }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<PlaceFragmentBinding>(
            inflater,
            R.layout.place_fragment,
            container,
            false
        )
        binding.searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility= View.VISIBLE
                viewModel.placeList.clear()
                adapter?.notifyDataSetChanged()
            }
        }
        adapter = PlaceAdapter(viewModel.placeList)
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
            val places = result.getOrNull()
            if (places != null) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                Log.i("PlaceFragment", "places: ${viewModel.placeList}")
                adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter =adapter
            it.visibility = View.VISIBLE
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        adapter?.setOnItemClick(object : CommonAdapter.OnItemClick<Place> {
            override fun onItemClick(position: Int, view: View, data: Place) {
                if (activity is WeatherActivity) {
                    val weatherActivity = activity as WeatherActivity
                    weatherActivity.drawerLayout?.closeDrawers()
                    weatherActivity.viewModel.locationLng = data.location.lng
                    weatherActivity.viewModel.locationLat = data.location.lat
                    weatherActivity.viewModel.placeName = data.name
                    weatherActivity.refreshWeather()
                } else {
                    val intent = Intent(this@PlaceFragment.activity, WeatherActivity::class.java).apply {
                        putExtra("location_lng", data.location.lng)
                        putExtra("location_lat", data.location.lat)
                        putExtra("place_name", data.name)
                    }
                    startActivity(intent)
                    activity?.finish()
                }
                viewModel.savePlace(data)
            }
        })
    }
}