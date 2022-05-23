package com.example.pick_country_list

import android.text.TextUtils
import com.example.pick_country_list.Country
import com.example.pick_country_list.Country.ISOCodeComparator
import android.annotation.SuppressLint
import android.telephony.TelephonyManager
import com.example.pick_country_list.R
import androidx.appcompat.app.AppCompatActivity
import com.example.pick_country_list.CountryPicker
import android.widget.TextView
import android.widget.EditText
import android.os.Bundle
import com.example.pick_country_list.CountryPickerListener
import android.text.TextWatcher
import android.text.Editable
import com.example.pick_country_list.CountryAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.widget.BaseAdapter

enum class Theme {
    DARK, LIGHT
}