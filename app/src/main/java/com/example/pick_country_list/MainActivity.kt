package com.example.pick_country_list

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    var picker: CountryPicker? = null
    var buttonOpen: Button? = null
    private var countryDialCode: TextView? = null
    private var isBackspaceClicked = false
    private var countryDialog: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonOpen = findViewById(R.id.buttonOpen)
        countryDialCode = findViewById(R.id.countryDialCode)
        countryDialog = findViewById(R.id.countryDialog)
        picker = CountryPicker.Companion.newInstance(
            "Select Country",
            Resources.getSystem().newTheme()
        ) // Set Dialog Title and Theme
        picker?.setListener(object : CountryPickerListener {
            override fun onSelectCountry(name: String?, code: String?, dialCode: String?, flag: Int) {
                val countryCode = findViewById<View>(R.id.countryCode) as TextView

//            EditText countryName = (EditText)findViewById(R.id.countryName);
//            EditText countryDialCode = (EditText)findViewById(R.id.countryDialCode);
                val countryIcon = findViewById<View>(R.id.countryIcon) as ImageView
                countryCode?.text = code
                //            countryName.setText(name);
                countryDialCode?.setText(dialCode)
                countryDialog?.setText("")
                //            countryDialog.setSelection((dialCode+"\t").length());
                countryIcon?.setImageResource(flag)

                //save length
                //3
                picker?.dismiss()
            }

        })
        countryDialog?.addTextChangedListener(object : TextWatcher {
            //
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                isBackspaceClicked = if (after < count) {
                    true
                } else {
                    false
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val phoneInput = countryDialog?.getText().toString().trim { it <= ' ' }
                buttonOpen?.setEnabled(!phoneInput.isEmpty())
            }

            override fun afterTextChanged(s: Editable) {
                if (!isBackspaceClicked) {
                    val numberInput = countryDialog?.getText().toString()
                    if (numberInput.length % 4 == 0) {
                        //+848888
                        //8888
                        //+1058888
                        //setText($code + " " + 8888)
                        countryDialog?.setText(countryDialog?.getText().toString() + " ")
                        countryDialog?.setSelection(countryDialog?.getText()!!.length)
                    }
                } else {
                    // Your "backspace" handling
                }
            }
        })
        val countryIcon = findViewById<View>(R.id.countryIcon) as ImageView
        countryIcon.setOnClickListener {
            picker?.show(
                supportFragmentManager, "COUNTRY_PICKER"
            )
        }
    }
}