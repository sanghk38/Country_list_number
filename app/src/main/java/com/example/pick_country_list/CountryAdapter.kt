package com.example.pick_country_list

import android.text.TextUtils
import com.example.pick_country_list.Country
import com.example.pick_country_list.Country.ISOCodeComparator
import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import com.example.pick_country_list.R
import androidx.appcompat.app.AppCompatActivity
import com.example.pick_country_list.CountryPicker
import android.os.Bundle
import com.example.pick_country_list.CountryPickerListener
import android.text.TextWatcher
import android.text.Editable
import com.example.pick_country_list.CountryAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener

class CountryAdapter(
    private val mContext: Context?,
    var countries: List<Country?>,
    var theme: Theme?
) : BaseAdapter() {
    var inflater: LayoutInflater
    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(arg0: Int): Any? {
        return null
    }

    override fun getItemId(arg0: Int): Long {
        return 0L
    }

//    override fun getView(position: Int, view: View, parent: ViewGroup): View {
//        var view = view
//        val country = countries[position]
//        if (view == null) {
//            view = if (theme == Theme.DARK) {
//                inflater.inflate(R.layout.row_dark, null as ViewGroup?)
//            } else {
//                inflater.inflate(R.layout.row, null as ViewGroup?)
//            }
//        }
//        val cell = Cell.from(view)
//        cell!!.textView?.setText(country!!.name)
//        country!!.loadFlagByCode(mContext)
//        if (country.flag != -1) {
//            cell.imageView!!.setImageResource(country.flag)
//        }
//        return view
//    }

    override fun getView(position: Int, view: View?, p2: ViewGroup?): View? {
        var view = view
        val country = countries[position]
        if (view == null) {
            view = if (theme == Theme.DARK) {
                inflater.inflate(R.layout.row, null as ViewGroup?)
            } else {
                inflater.inflate(R.layout.row, null as ViewGroup?)
            }
        }
        val cell = Cell.from(view)
        cell!!.textView?.setText(country!!.name)
        country!!.loadFlagByCode(mContext)
        if (country.flag != -1) {
            cell.imageView!!.setImageResource(country.flag)
        }
        return view
    }

    internal class Cell {
        var textView: TextView? = null
        var imageView: ImageView? = null

        companion object {
            fun from(view: View?): Cell? {
                return if (view == null) {
                    null
                } else if (view.tag == null) {
                    val cell = Cell()
                    cell.textView = view.findViewById<View>(R.id.row_title) as TextView
                    cell.imageView =
                        view.findViewById<View>(R.id.row_icon) as ImageView
                    view.tag = cell
                    cell
                } else {
                    view.tag as Cell
                }
            }
        }
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}