package com.example.assignment08

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.example.assignment08.databinding.FragmentSecondBinding
import com.example.assignment08.databinding.StockCellLayoutBinding
import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment(model: SecondFragmentModel) : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val settings = context?.getSharedPreferences("stockFile",
        Context.MODE_PRIVATE)
    private val editor = settings?.edit()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val model: SecondFragmentModel by activityViewModels()
        val adapter = StockAdapter(mutableListOf<Stock>(), requireContext());
        binding.listStockView.adapter = adapter

        model.items.observe(viewLifecycleOwner, Observer<MutableList<Stock>>{
            adapter.stocks.clear()
            adapter.stocks.addAll(it)
        })


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        val savedStockItems: Map<String, String> = settings?.all as Map<String, String>


        val savedItems = mutableListOf<Stock>()

        savedStockItems.forEach{
            savedItems.add(Stock(it.key, it.value))
        }

        binding.listStockView.adapter = StockAdapter(savedItems, requireContext());
    }

}

class StockAdapter(var stocks: MutableList<Stock>, val context: Context) : BaseAdapter() {
    var layoutInflater: LayoutInflater
    private var _binding: StockCellLayoutBinding? = null
    private val binding get() = _binding!!
    private var bindings = mutableMapOf<View, StockCellLayoutBinding>()

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override
    fun getCount(): Int { //number of elements to display
        return stocks.size
    }

    override fun getItem(index: Int): Stock { //item at index
        return stocks.get(index)
    }

    override fun getItemId(index: Int): Long { //itemId for index
        return index.toLong()
    }

    override fun getView(
        index: Int, oldView: View?,
        viewGroup: ViewGroup?
    ): View {
        var view: View
        if (oldView == null) { //check if we get a view to recycle
            _binding = StockCellLayoutBinding.inflate(layoutInflater, viewGroup, false)
            view = binding.root;bindings[binding.root] = binding
        } else { //if yes, use the oldview
            view = oldView
            _binding = bindings[view]
        }
        val stock = getItem(index) //get the data for this index
        binding.stockName.text = (index+1).toString()
        binding.stockSymbol.text = stock.symbol
        binding.stockValue.text = stock.value
        return view
    }

}

class Stock(
    @Json(name = "01. symbol")
    var symbol: String,
    @Json(name = "05. price")
    var value: String

) {}

class StockKlaxonBase(
    @Json(name = "Global Quote")
    val globalQuote : Stock
    )
