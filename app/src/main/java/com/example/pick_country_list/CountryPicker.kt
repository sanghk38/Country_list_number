package com.example.pick_country_list


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources

import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.DialogFragment
import java.util.*

class CountryPicker : DialogFragment() {
    var searchEditText: EditText? = null
    var countryListView: ListView? = null
    var adapter: CountryAdapter? = null
    var countriesList: MutableList<Country?> = ArrayList()
    var selectedCountriesList: MutableList<Country?> = ArrayList()
    var listener: CountryPickerListener? = null
//    var context: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.country_picker, null as ViewGroup?)
        val args = this.arguments
        if (args != null) {
            val dialogTitle = args.getString("dialogTitle")
            this.dialog!!.setTitle(dialogTitle)
            val width = this.resources.getDimensionPixelSize(R.dimen.cp_dialog_width)
            val height = this.resources.getDimensionPixelSize(R.dimen.cp_dialog_height)
            this.dialog!!.window!!.setLayout(width, height)
        }
        if (pickerTheme!!.equals(Theme.DARK)) {
            view = inflater.inflate(R.layout.country_picker, null as ViewGroup?)
        }
        searchEditText = view.findViewById(R.id.country_code_picker_search)
        countryListView = view.findViewById(R.id.country_code_picker_listview)
        selectedCountriesList = ArrayList<Country?>(countriesList.size)
        selectedCountriesList.addAll(countriesList)
        adapter = CountryAdapter(this.activity, selectedCountriesList, Theme.DARK)
        countryListView?.setAdapter(adapter)
        countryListView?.setOnItemClickListener(OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
            if (listener != null) {
                val country = selectedCountriesList[position]
                listener!!.onSelectCountry(
                    country?.name,
                    country?.getCode(),
                    country?.dialCode,
                    country?.flag!!
                )
            }
        })
        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                search(s.toString())
            }
        })
        return view
    }

    @JvmName("setListener1")
    fun setListener(listener: CountryPickerListener?)
    {
        this.listener = listener
    }

    @SuppressLint("DefaultLocale")
    private fun search(text: String) {
        selectedCountriesList.clear()
        for (country in countriesList) {
            if (country!!.name?.toLowerCase(Locale.ENGLISH)!!.contains(text.toLowerCase())) {
                selectedCountriesList.add(country)
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    @JvmName("setCountriesList1")
    fun setCountriesList(newCountries: List<Country?>?) {
        countriesList.clear()
        countriesList.addAll(newCountries!!)
    }

    companion object {
        private var pickerTheme: Resources.Theme? = null
        fun newInstance(dialogTitle: String?, theme: Resources.Theme?): CountryPicker {
            val picker = CountryPicker()
            val bundle = Bundle()
            bundle.putString("dialogTitle", dialogTitle)
            pickerTheme = theme
            picker.arguments = bundle
            return picker
        }
    }

    init {
        setCountriesList(Country.allCountries)
    }
}