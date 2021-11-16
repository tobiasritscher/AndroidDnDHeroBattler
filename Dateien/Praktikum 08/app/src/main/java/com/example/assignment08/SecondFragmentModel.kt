package com.example.assignment08

import android.content.Context
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

    }

    fun loadStock(context: Context) {
        val settings = context?.getSharedPreferences(
            "stockFile",
            Context.MODE_PRIVATE
        )
        val editor = settings?.edit()


        //create a request queue
        val requestQueue = Volley.newRequestQueue(requireContext())
        //define a request.

        val ENDPOINT =
            "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=B6BHJ8VQ01NS13RC"

        val request = StringRequest(
            Request.Method.GET, ENDPOINT,
            { response ->
                val stockBase = Klaxon().parse<StockKlaxonBase>(response)
                Log.i("ibm", stockBase!!.globalQuote!!.symbol)

                val items = mutableListOf(
                    stockBase!!.globalQuote,
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


            },

            {
                //use the porvided VolleyError to display
                //an error message
            })
        //add the call to the request queue
        requestQueue.add(request)
    }
}