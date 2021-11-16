package com.example.assignment08

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class SecondFragmentModel(): ViewModel() {
    var items = MutableLiveData<MutableList<Stock>>();



    init {
        //
    }

    fun initStock(editor: SharedPreferences.Editor) {

        val items = mutableListOf(
            Stock("AAPL", "115.69"),
            Stock("MSFT", "214.36"),
            Stock("GOOGL", "1519.45"),
            Stock("CRM", "255.52"),
            Stock("FB", "260.02")
        )

        //writes into settings localy
        items.forEach {
            editor?.putString(it.symbol, it.value)
        }
        editor.apply()

    }

    fun loadStock(settings: SharedPreferences){
        val savedStockItems: Map<String, String> = settings?.all as Map<String, String>

        val savedItems = mutableListOf<Stock>()

        savedStockItems.forEach{
            savedItems.add(Stock(it.key, it.value))
        }

        items.value = savedItems
    }

    fun saveStock(editor: SharedPreferences.Editor, symbol: String, value: String){
        editor?.putString(symbol, value)
        editor.apply()
    }
}