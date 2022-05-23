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
import java.lang.Exception
import java.util.*

class Country {
    private var code: String? = null
    var name: String? = null
        private set
    var dialCode: String? = null
    var flag = -1

    constructor(code: String?, name: String?, dialCode: String?, flag: Int) {
        this.code = code
        this.name = name
        this.dialCode = dialCode
        this.flag = flag
    }

    constructor() {}

    fun getCode(): String? {
        return this.code
    }

    fun setCode(code: String?) {
        this.code = code
        if (TextUtils.isEmpty(name)) {
            name = Locale("", code).displayName
        }
    }

    fun loadFlagByCode(context: Context?) {
        if (flag == -1) {
            try {
                flag = context!!.resources.getIdentifier(
                    "flag_" + this.code!!.toLowerCase(Locale.ENGLISH),
                    "drawable",
                    context.packageName
                )
            } catch (var3: Exception) {
                var3.printStackTrace()
                flag = -1
            }
        }
    }

    companion object {
        val COUNTRIES: Array<Country>
        private var allCountriesList: List<Country?>? = null
        val allCountries: List<Country?>?
            get() {
                if (allCountriesList == null) {
                    allCountriesList = Arrays.asList(*COUNTRIES)
                }
                return allCountriesList
            }

        fun getCountryByISO(countryIsoCode: String): Country? {
            var countryIsoCode = countryIsoCode
            countryIsoCode = countryIsoCode.toUpperCase()
            val c = Country()
            c.setCode(countryIsoCode)
            val i = Arrays.binarySearch(COUNTRIES, c, ISOCodeComparator())
            return if (i < 0) null else COUNTRIES[i]
        }

        fun getCountryByName(countryName: String): Country? {
            val var1 = COUNTRIES
            val var2 = var1.size
            for (c in var1) {
                if (countryName == c.name) {
                    return c
                }
            }
            return null
        }

        fun getCountryByLocale(locale: Locale): Country? {
            val countryIsoCode = locale.isO3Country.substring(0, 2).toLowerCase()
            return getCountryByISO(countryIsoCode)
        }

        @SuppressLint("WrongConstant")
        fun getCountryFromSIM(context: Context): Country? {
            @SuppressLint("WrongConstant") val telephonyManager =
                context.getSystemService("phone") as TelephonyManager
            return if (telephonyManager.simState != 1) getCountryByISO(telephonyManager.simCountryIso) else null
        }

        //    static {
        //        COUNTRIES = new Country[]{new Country("AD", "Andorra", "+376", R.drawable.flag_ad),
        //                new Country("AE", "United Arab Emirates", "+971",
        //                        R.drawable.flag_ae), new Country("AF", "Afghanistan",
        //                "+93", R.drawable.flag_af), new Country("AG", "Antigua and Barbuda",
        //                "+1", R.drawable.flag_ag), new Country("AI", "Anguilla", "+1", R.drawable.flag_ai), new Country("AL", "Albania", "+355", R.drawable.flag_al), new Country("AM", "Armenia", "+374", R.drawable.flag_am), new Country("AO", "Angola", "+244", R.drawable.flag_ao), new Country("AQ", "Antarctica", "+672", R.drawable.flag_aq), new Country("AR", "Argentina", "+54", R.drawable.flag_ar), new Country("AS", "AmericanSamoa", "+1", R.drawable.flag_as), new Country("AT", "Austria", "+43", R.drawable.flag_at), new Country("AU", "Australia", "+61", R.drawable.flag_au), new Country("AW", "Aruba", "+297", R.drawable.flag_aw), new Country("AX", "Åland Islands", "+358", R.drawable.flag_ax), new Country("AZ", "Azerbaijan", "+994", R.drawable.flag_az), new Country("BA", "Bosnia and Herzegovina", "+387", R.drawable.flag_ba), new Country("BB", "Barbados", "+1", R.drawable.flag_bb), new Country("BD", "Bangladesh", "+880", R.drawable.flag_bd), new Country("BE", "Belgium", "+32", R.drawable.flag_be), new Country("BF", "Burkina Faso", "+226", R.drawable.flag_bf), new Country("BG", "Bulgaria", "+359", R.drawable.flag_bg), new Country("BH", "Bahrain", "+973", R.drawable.flag_bh), new Country("BI", "Burundi", "+257", R.drawable.flag_bi), new Country("BJ", "Benin", "+229", R.drawable.flag_bj), new Country("BL", "Saint Barthélemy", "+590", R.drawable.flag_bl), new Country("BM", "Bermuda", "+1", R.drawable.flag_bm), new Country("BN", "Brunei Darussalam", "+673", R.drawable.flag_bn), new Country("BO", "Bolivia, Plurinational State of", "+591", R.drawable.flag_bo), new Country("BQ", "Bonaire", "+599", R.drawable.flag_bq), new Country("BR", "Brazil", "+55", R.drawable.flag_br), new Country("BS", "Bahamas", "+1", R.drawable.flag_bs), new Country("BT", "Bhutan", "+975", R.drawable.flag_bt), new Country("BV", "Bouvet Island", "+47", R.drawable.flag_bv), new Country("BW", "Botswana", "+267", R.drawable.flag_bw), new Country("BY", "Belarus", "+375", R.drawable.flag_by), new Country("BZ", "Belize", "+501", R.drawable.flag_bz), new Country("CA", "Canada", "+1", R.drawable.flag_ca), new Country("CC", "Cocos (Keeling) Islands", "+61", R.drawable.flag_cc), new Country("CD", "Congo, The Democratic Republic of the", "+243", R.drawable.flag_cd), new Country("CF", "Central African Republic", "+236", R.drawable.flag_cf), new Country("CG", "Congo", "+242", R.drawable.flag_cg), new Country("CH", "Switzerland", "+41", R.drawable.flag_ch), new Country("CI", "Ivory Coast", "+225", R.drawable.flag_ci), new Country("CK", "Cook Islands", "+682", R.drawable.flag_ck), new Country("CL", "Chile", "+56", R.drawable.flag_cl), new Country("CM", "Cameroon", "+237", R.drawable.flag_cm), new Country("CN", "China", "+86", R.drawable.flag_cn), new Country("CO", "Colombia", "+57", R.drawable.flag_co), new Country("CR", "Costa Rica", "+506", R.drawable.flag_cr), new Country("CU", "Cuba", "+53", R.drawable.flag_cu), new Country("CV", "Cape Verde", "+238", R.drawable.flag_cv), new Country("CW", "Curacao", "+599", R.drawable.flag_cw), new Country("CX", "Christmas Island", "+61", R.drawable.flag_cx), new Country("CY", "Cyprus", "+357", R.drawable.flag_cy), new Country("CZ", "Czech Republic", "+420", R.drawable.flag_cz), new Country("DE", "Germany", "+49", R.drawable.flag_de), new Country("DJ", "Djibouti", "+253", R.drawable.flag_dj), new Country("DK", "Denmark", "+45", R.drawable.flag_dk), new Country("DM", "Dominica", "+1", R.drawable.flag_dm), new Country("DO", "Dominican Republic", "+1", R.drawable.flag_do), new Country("DZ", "Algeria", "+213", R.drawable.flag_dz), new Country("EC", "Ecuador", "+593", R.drawable.flag_ec), new Country("EE", "Estonia", "+372", R.drawable.flag_ee), new Country("EG", "Egypt", "+20", R.drawable.flag_eg), new Country("EH", "Western Sahara", "+212", R.drawable.flag_eh), new Country("ER", "Eritrea", "+291", R.drawable.flag_er), new Country("ES", "Spain", "+34", R.drawable.flag_es), new Country("ET", "Ethiopia", "+251", R.drawable.flag_et), new Country("FI", "Finland", "+358", R.drawable.flag_fi), new Country("FJ", "Fiji", "+679", R.drawable.flag_fj), new Country("FK", "Falkland Islands (Malvinas)", "+500", R.drawable.flag_fk), new Country("FM", "Micronesia, Federated States of", "+691", R.drawable.flag_fm), new Country("FO", "Faroe Islands", "+298", R.drawable.flag_fo), new Country("FR", "France", "+33", R.drawable.flag_fr), new Country("GA", "Gabon", "+241", R.drawable.flag_ga), new Country("GB", "United Kingdom", "+44", R.drawable.flag_gb), new Country("GD", "Grenada", "+1", R.drawable.flag_gd), new Country("GE", "Georgia", "+995", R.drawable.flag_ge), new Country("GF", "French Guiana", "+594", R.drawable.flag_gf), new Country("GG", "Guernsey", "+44", R.drawable.flag_gg), new Country("GH", "Ghana", "+233", R.drawable.flag_gh), new Country("GI", "Gibraltar", "+350", R.drawable.flag_gi), new Country("GL", "Greenland", "+299", R.drawable.flag_gl), new Country("GM", "Gambia", "+220", R.drawable.flag_gm), new Country("GN", "Guinea", "+224", R.drawable.flag_gn), new Country("GP", "Guadeloupe", "+590", R.drawable.flag_gp), new Country("GQ", "Equatorial Guinea", "+240", R.drawable.flag_gq), new Country("GR", "Greece", "+30", R.drawable.flag_gr), new Country("GS", "South Georgia and the South Sandwich Islands", "+500", R.drawable.flag_gs), new Country("GT", "Guatemala", "+502", R.drawable.flag_gt), new Country("GU", "Guam", "+1", R.drawable.flag_gu), new Country("GW", "Guinea-Bissau", "+245", R.drawable.flag_gw), new Country("GY", "Guyana", "+595", R.drawable.flag_gy), new Country("HK", "Hong Kong", "+852", R.drawable.flag_hk), new Country("HM", "Heard Island and McDonald Islands", "", R.drawable.flag_hm), new Country("HN", "Honduras", "+504", R.drawable.flag_hn), new Country("HR", "Croatia", "+385", R.drawable.flag_hr), new Country("HT", "Haiti", "+509", R.drawable.flag_ht), new Country("HU", "Hungary", "+36", R.drawable.flag_hu), new Country("ID", "Indonesia", "+62", R.drawable.flag_id), new Country("IE", "Ireland", "+353", R.drawable.flag_ie), new Country("IL", "Israel", "+972", R.drawable.flag_il), new Country("IM", "Isle of Man", "+44", R.drawable.flag_im), new Country("IN", "India", "+91", R.drawable.flag_in), new Country("IO", "British Indian Ocean Territory", "+246", R.drawable.flag_io), new Country("IQ", "Iraq", "+964", R.drawable.flag_iq), new Country("IR", "Iran, Islamic Republic of", "+98", R.drawable.flag_ir), new Country("IS", "Iceland", "+354", R.drawable.flag_is), new Country("IT", "Italy", "+39", R.drawable.flag_it), new Country("JE", "Jersey", "+44", R.drawable.flag_je), new Country("JM", "Jamaica", "+1", R.drawable.flag_jm), new Country("JO", "Jordan", "+962", R.drawable.flag_jo), new Country("JP", "Japan", "+81", R.drawable.flag_jp), new Country("KE", "Kenya", "+254", R.drawable.flag_ke), new Country("KG", "Kyrgyzstan", "+996", R.drawable.flag_kg), new Country("KH", "Cambodia", "+855", R.drawable.flag_kh), new Country("KI", "Kiribati", "+686", R.drawable.flag_ki), new Country("KM", "Comoros", "+269", R.drawable.flag_km), new Country("KN", "Saint Kitts and Nevis", "+1", R.drawable.flag_kn), new Country("KP", "North Korea", "+850", R.drawable.flag_kp), new Country("KR", "South Korea", "+82", R.drawable.flag_kr), new Country("KW", "Kuwait", "+965", R.drawable.flag_kw), new Country("KY", "Cayman Islands", "+345", R.drawable.flag_ky), new Country("KZ", "Kazakhstan", "+7", R.drawable.flag_kz), new Country("LA", "Lao People's Democratic Republic", "+856", R.drawable.flag_la), new Country("LB", "Lebanon", "+961", R.drawable.flag_lb), new Country("LC", "Saint Lucia", "+1", R.drawable.flag_lc), new Country("LI", "Liechtenstein", "+423", R.drawable.flag_li), new Country("LK", "Sri Lanka", "+94", R.drawable.flag_lk), new Country("LR", "Liberia", "+231", R.drawable.flag_lr), new Country("LS", "Lesotho", "+266", R.drawable.flag_ls), new Country("LT", "Lithuania", "+370", R.drawable.flag_lt), new Country("LU", "Luxembourg", "+352", R.drawable.flag_lu), new Country("LV", "Latvia", "+371", R.drawable.flag_lv), new Country("LY", "Libyan Arab Jamahiriya", "+218", R.drawable.flag_ly), new Country("MA", "Morocco", "+212", R.drawable.flag_ma), new Country("MC", "Monaco", "+377", R.drawable.flag_mc), new Country("MD", "Moldova, Republic of", "+373", R.drawable.flag_md), new Country("ME", "Montenegro", "+382", R.drawable.flag_me), new Country("MF", "Saint Martin", "+590", R.drawable.flag_mf), new Country("MG", "Madagascar", "+261", R.drawable.flag_mg), new Country("MH", "Marshall Islands", "+692", R.drawable.flag_mh), new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", R.drawable.flag_mk), new Country("ML", "Mali", "+223", R.drawable.flag_ml), new Country("MM", "Myanmar", "+95", R.drawable.flag_mm), new Country("MN", "Mongolia", "+976", R.drawable.flag_mn), new Country("MO", "Macao", "+853", R.drawable.flag_mo), new Country("MP", "Northern Mariana Islands", "+1", R.drawable.flag_mp), new Country("MQ", "Martinique", "+596", R.drawable.flag_mq), new Country("MR", "Mauritania", "+222", R.drawable.flag_mr), new Country("MS", "Montserrat", "+1", R.drawable.flag_ms), new Country("MT", "Malta", "+356", R.drawable.flag_mt), new Country("MU", "Mauritius", "+230", R.drawable.flag_mu), new Country("MV", "Maldives", "+960", R.drawable.flag_mv), new Country("MW", "Malawi", "+265", R.drawable.flag_mw), new Country("MX", "Mexico", "+52", R.drawable.flag_mx), new Country("MY", "Malaysia", "+60", R.drawable.flag_my), new Country("MZ", "Mozambique", "+258", R.drawable.flag_mz), new Country("NA", "Namibia", "+264", R.drawable.flag_na), new Country("NC", "New Caledonia", "+687", R.drawable.flag_nc), new Country("NE", "Niger", "+227", R.drawable.flag_ne), new Country("NF", "Norfolk Island", "+672", R.drawable.flag_nf), new Country("NG", "Nigeria", "+234", R.drawable.flag_ng), new Country("NI", "Nicaragua", "+505", R.drawable.flag_ni), new Country("NL", "Netherlands", "+31", R.drawable.flag_nl), new Country("NO", "Norway", "+47", R.drawable.flag_no), new Country("NP", "Nepal", "+977", R.drawable.flag_np), new Country("NR", "Nauru", "+674", R.drawable.flag_nr), new Country("NU", "Niue", "+683", R.drawable.flag_nu), new Country("NZ", "New Zealand", "+64", R.drawable.flag_nz), new Country("OM", "Oman", "+968", R.drawable.flag_om), new Country("PA", "Panama", "+507", R.drawable.flag_pa), new Country("PE", "Peru", "+51", R.drawable.flag_pe), new Country("PF", "French Polynesia", "+689", R.drawable.flag_pf), new Country("PG", "Papua New Guinea", "+675", R.drawable.flag_pg), new Country("PH", "Philippines", "+63", R.drawable.flag_ph), new Country("PK", "Pakistan", "+92", R.drawable.flag_pk), new Country("PL", "Poland", "+48", R.drawable.flag_pl), new Country("PM", "Saint Pierre and Miquelon", "+508", R.drawable.flag_pm), new Country("PN", "Pitcairn", "+872", R.drawable.flag_pn), new Country("PR", "Puerto Rico", "+1", R.drawable.flag_pr), new Country("PS", "Palestinian Territory, Occupied", "+970", R.drawable.flag_ps), new Country("PT", "Portugal", "+351", R.drawable.flag_pt), new Country("PW", "Palau", "+680", R.drawable.flag_pw), new Country("PY", "Paraguay", "+595", R.drawable.flag_py), new Country("QA", "Qatar", "+974", R.drawable.flag_qa), new Country("RE", "Réunion", "+262", R.drawable.flag_re), new Country("RO", "Romania", "+40", R.drawable.flag_ro), new Country("RS", "Serbia", "+381", R.drawable.flag_rs), new Country("RU", "Russia", "+7", R.drawable.flag_ru), new Country("RW", "Rwanda", "+250", R.drawable.flag_rw), new Country("SA", "Saudi Arabia", "+966", R.drawable.flag_sa), new Country("SB", "Solomon Islands", "+677", R.drawable.flag_sb), new Country("SC", "Seychelles", "+248", R.drawable.flag_sc), new Country("SD", "Sudan", "+249", R.drawable.flag_sd), new Country("SE", "Sweden", "+46", R.drawable.flag_se), new Country("SG", "Singapore", "+65", R.drawable.flag_sg), new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", R.drawable.flag_sh), new Country("SI", "Slovenia", "+386", R.drawable.flag_si), new Country("SJ", "Svalbard and Jan Mayen", "+47", R.drawable.flag_sj), new Country("SK", "Slovakia", "+421", R.drawable.flag_sk), new Country("SL", "Sierra Leone", "+232", R.drawable.flag_sl), new Country("SM", "San Marino", "+378", R.drawable.flag_sm), new Country("SN", "Senegal", "+221", R.drawable.flag_sn), new Country("SO", "Somalia", "+252", R.drawable.flag_so), new Country("SR", "Suriname", "+597", R.drawable.flag_sr), new Country("SS", "South Sudan", "+211", R.drawable.flag_ss), new Country("ST", "Sao Tome and Principe", "+239", R.drawable.flag_st), new Country("SV", "El Salvador", "+503", R.drawable.flag_sv), new Country("SX", "  Sint Maarten", "+1", R.drawable.flag_sx), new Country("SY", "Syrian Arab Republic", "+963", R.drawable.flag_sy), new Country("SZ", "Swaziland", "+268", R.drawable.flag_sz), new Country("TC", "Turks and Caicos Islands", "+1", R.drawable.flag_tc), new Country("TD", "Chad", "+235", R.drawable.flag_td), new Country("TF", "French Southern Territories", "+262", R.drawable.flag_tf), new Country("TG", "Togo", "+228", R.drawable.flag_tg), new Country("TH", "Thailand", "+66", R.drawable.flag_th), new Country("TJ", "Tajikistan", "+992", R.drawable.flag_tj), new Country("TK", "Tokelau", "+690", R.drawable.flag_tk), new Country("TL", "East Timor", "+670", R.drawable.flag_tl), new Country("TM", "Turkmenistan", "+993", R.drawable.flag_tm), new Country("TN", "Tunisia", "+216", R.drawable.flag_tn), new Country("TO", "Tonga", "+676", R.drawable.flag_to), new Country("TR", "Turkey", "+90", R.drawable.flag_tr), new Country("TT", "Trinidad and Tobago", "+1", R.drawable.flag_tt), new Country("TV", "Tuvalu", "+688", R.drawable.flag_tv), new Country("TW", "Taiwan", "+886", R.drawable.flag_tw), new Country("TZ", "Tanzania, United Republic of", "+255", R.drawable.flag_tz), new Country("UA", "Ukraine", "+380", R.drawable.flag_ua), new Country("UG", "Uganda", "+256", R.drawable.flag_ug), new Country("UM", "U.S. Minor Outlying Islands", "", R.drawable.flag_um), new Country("US", "United States", "+1", R.drawable.flag_us), new Country("UY", "Uruguay", "+598", R.drawable.flag_uy), new Country("UZ", "Uzbekistan", "+998", R.drawable.flag_uz), new Country("VA", "Holy See (Vatican City State)", "+379", R.drawable.flag_va), new Country("VC", "Saint Vincent and the Grenadines", "+1", R.drawable.flag_vc), new Country("VE", "Venezuela, Bolivarian Republic of", "+58", R.drawable.flag_ve), new Country("VG", "Virgin Islands, British", "+1", R.drawable.flag_vg), new Country("VI", "Virgin Islands, U.S.", "+1", R.drawable.flag_vi), new Country("VN", "Viet Nam", "+84", R.drawable.flag_vn), new Country("VU", "Vanuatu", "+678", R.drawable.flag_vu), new Country("WF", "Wallis and Futuna", "+681", R.drawable.flag_wf), new Country("WS", "Samoa", "+685", R.drawable.flag_ws), new Country("XK", "Kosovo", "+383",R.drawable.flag_xk), new Country("YE", "Yemen", "+967", R.drawable.flag_ye), new Country("YT", "Mayotte", "+262", R.drawable.flag_yt), new Country("ZA", "South Africa", "+27", R.drawable.flag_za), new Country("ZM", "Zambia", "+260", R.drawable.flag_zm), new Country("ZW", "Zimbabwe", "+263", R.drawable.flag_zw)};
        //    }
        init {
            COUNTRIES = arrayOf(
                Country("AF", "Afghanistan", "+93", R.drawable.f000),
                Country("AL", "Algeria", "+355", R.drawable.f001),
                Country("DZ", "Algeria", "+213", R.drawable.f002),
                Country("AS", "American Samoa", "+1684", R.drawable.f003),
                Country("AD", "Andorra", "+376", R.drawable.f004),
                Country("AO", "Angola", "+244", R.drawable.f005),
                Country("AI", "Anguilla", "+1264", R.drawable.f006),
                Country("AG", "Antigua and Barbuda", "+1268", R.drawable.f007),
                Country("AR", "Argentina", "+54", R.drawable.f008),
                Country("AM", "Armenia", "+374", R.drawable.f009),
                Country("AW", "Aruba", "+297", R.drawable.f010),
                Country("AU", "Australia", "+61", R.drawable.f011),
                Country("AT", "Austria", "+43", R.drawable.f012),
                Country("AZ", "Azerbaijan", "+994", R.drawable.f013),
                Country("BS", "Bahamas", "+1242", R.drawable.f014),
                Country("BH", "Bahrain", "+973", R.drawable.f015),
                Country("BD", "Bangladesh", "+880", R.drawable.f016),
                Country("BB", "Barbados", "+1246", R.drawable.f017),
                Country("BY", "Belarus", "+375", R.drawable.f018),
                Country("BE", "Belgium", "+32", R.drawable.f019),
                Country("BZ", "Belize", "+501", R.drawable.f020),
                Country("BJ", "Benin", "+229", R.drawable.f021),
                Country("BM", "Bermuda", "+1441", R.drawable.f022),
                Country("BT", "Bhutan", "+975", R.drawable.f023),
                Country("BO", "Bolivia", "+591", R.drawable.f024),
                Country("BA", "Bosnia and Herzegovina", "+387", R.drawable.f025),
                Country("BW", "Botswana", "+267", R.drawable.f026),
                Country("BR", "Brazil", "+55", R.drawable.f027),
                Country("IO", "British Indian Ocean Territory", "+246", R.drawable.f028),
                Country("VG", "British Virgin Islands", "+1284", R.drawable.f029),
                Country("BN", "Brunei", "+673", R.drawable.f030),
                Country("BG", "Bulgaria", "+359", R.drawable.f031),
                Country("BF", "Burkina Faso", "+226", R.drawable.f032),
                Country("BI", "Burundi", "+257", R.drawable.f033),
                Country("KH", "Cambodia", "+855", R.drawable.f034),
                Country("CM", "Cameroon", "+237", R.drawable.f035),
                Country("CA", "Canada", "+1", R.drawable.f036),
                Country("BQ", "Cape Verde", "+238", R.drawable.f037),
                Country("BQ", "Caribbean Netherlands", "+599", R.drawable.f038),
                Country("KY", "Cayman Islands", "+1345", R.drawable.f039),
                Country("CF", "Central African Republic", "+236", R.drawable.f040),
                Country("TD", "Chad", "+235", R.drawable.f041),
                Country("CL", "Chile", "+56", R.drawable.f042),
                Country("CN", "China", "+86", R.drawable.f043),
                Country("CO", "Colombia", "+57", R.drawable.f044),
                Country("KM", "Comoros", "+269", R.drawable.f045),
                Country("CD", "Congo (DRC)", "+243", R.drawable.f046),
                Country("CG", "Congo (Republic)", "+242", R.drawable.f047),
                Country("CK", "Cook Islands", "+682", R.drawable.f048),
                Country("CR", "Costa Rica", "+506", R.drawable.f049),
                Country("CI", "Côte d’Ivoire", "+225", R.drawable.f050),
                Country("HR", "Croatia", "+385", R.drawable.f051),
                Country("CU", "Cuba", "+53", R.drawable.f052),
                Country("CW", "Curaçao", "+599", R.drawable.f053),
                Country("CY", "Cyprus", "+357", R.drawable.f054),
                Country("CZ", "Czech Republic", "+420", R.drawable.f055),
                Country("DK", "Denmark", "+45", R.drawable.f056),
                Country("DJ", "Djibouti", "+253", R.drawable.f057),
                Country("DM", "Dominica", "+1767", R.drawable.f058),
                Country("DO", "Dominican Republic", "+1", R.drawable.f059),
                Country("EC", "Ecuador", "+593", R.drawable.f060),
                Country("EG", "Egypt", "+20", R.drawable.f061),
                Country("SV", "El Salvador", "+503", R.drawable.f062),
                Country("GQ", "Equatorial Guinea", "+240", R.drawable.f063),
                Country("ER", "Eritrea", "+291", R.drawable.f064),
                Country("EE", "Estonia", "+372", R.drawable.f065),
                Country("ET", "Ethiopia", "+251", R.drawable.f066),
                Country("FK", "Falkland Islands", "+500", R.drawable.f067),
                Country("FO", "Faroe Islands", "+298", R.drawable.f068),
                Country("FJ", "Fiji", "+679", R.drawable.f069),
                Country("FI", "Finland", "+358", R.drawable.f070),
                Country("FR", "France", "+33", R.drawable.f071),
                Country("GF", "French Guiana", "+594", R.drawable.f072),
                Country("PF", "French Polynesia", "+689", R.drawable.f073),
                Country("GA", "Gabon", "+241", R.drawable.f074),
                Country("GM", "Gambia", "+220", R.drawable.f075),
                Country("GE", "Georgia", "+995", R.drawable.f076),
                Country("DE", "Germany", "+49", R.drawable.f077),
                Country("GH", "Ghana", "+233", R.drawable.f078),
                Country("GI", "Gibraltar", "+350", R.drawable.f079),
                Country("GR", "Greece", "+30", R.drawable.f080),
                Country("GL", "Greenland", "+299", R.drawable.f081),
                Country("GD", "Grenada", "+1473", R.drawable.f082),
                Country("GP", "Guadeloupe", "+590", R.drawable.f083),
                Country("GU", "Guam", "+1671", R.drawable.f084),
                Country("GT", "Guatemala", "+502", R.drawable.f085),
                Country("GN", "Guinea", "+224", R.drawable.f086),
                Country("GW", "Guinea-Bissau", "+245", R.drawable.f087),
                Country("GY", "Guyana", "+592", R.drawable.f088),
                Country("HT", "Haiti", "+509", R.drawable.f089),
                Country("HN", "Honduras", "+504", R.drawable.f090),
                Country("HK", "Hong Kong", "+852", R.drawable.f091),
                Country("HU", "Hungary", "+36", R.drawable.f092),
                Country("IS", "Iceland", "+354", R.drawable.f093),
                Country("IN", "India", "+91", R.drawable.f094),
                Country("ID", "Indonesia", "+62", R.drawable.f095),
                Country("IR", "Iran", "+98", R.drawable.f096),
                Country("IQ", "Iraq", "+964", R.drawable.f097),
                Country("IE", "Ireland", "+353", R.drawable.f098),
                Country("IL", "Israel", "+972", R.drawable.f099),
                Country("IT", "Italy", "+39", R.drawable.f100),
                Country("JM", "Jamaica", "+1876", R.drawable.f101),
                Country("JP", "Japan", "+81", R.drawable.f102),
                Country("JO", "Jordan", "+962", R.drawable.f103),
                Country("KZ", "Kazakhstan", "+7", R.drawable.f104),
                Country("KE", "Kenya", "+254", R.drawable.f105),
                Country("KI", "Kiribati", "+686", R.drawable.f106),
                Country("KW", "Kuwait", "+965", R.drawable.f107),
                Country("KG", "Kyrgyzstan", "+996", R.drawable.f108),
                Country("LA", "Laos", "+856", R.drawable.f109),
                Country("LV", "Latvia", "+371", R.drawable.f110),
                Country("LB", "Lebanon", "+961", R.drawable.f111),
                Country("LS", "Lesotho", "+266", R.drawable.f112),
                Country("LR", "Liberia", "+231", R.drawable.f113),
                Country("LY", "Libya", "+218", R.drawable.f114),
                Country("LI", "Liechtenstein", "+423", R.drawable.f115),
                Country("LT", "Lithuania", "+370", R.drawable.f116),
                Country("LU", "Luxembourg", "+352", R.drawable.f117),
                Country("MO", "Macau", "+853", R.drawable.f118),
                Country("MK", "Macedonia", "+389", R.drawable.f119),
                Country("MG", "Madagascar", "+261", R.drawable.f120),
                Country("MW", "Malawi", "+265", R.drawable.f121),
                Country("MY", "Malaysia", "+60", R.drawable.f122),
                Country("MV", "Maldives", "+960", R.drawable.f123),
                Country("ML", "Mali", "+223", R.drawable.f124),
                Country("MT", "Malta", "+356", R.drawable.f125),
                Country("MH", "Marshall Islands", "+692", R.drawable.f126),
                Country("MQ", "Martinique", "+596", R.drawable.f127),
                Country("MR", "Mauritania", "+222", R.drawable.f128),
                Country("MU", "Mauritius", "+230", R.drawable.f129),
                Country("MX", "Mexico", "+52", R.drawable.f130),
                Country("FM", "Micronesia", "+691", R.drawable.f131),
                Country("MD", "Moldova", "+373", R.drawable.f132),
                Country("MC", "Monaco", "+377", R.drawable.f133),
                Country("MN", "Mongolia", "+976", R.drawable.f134),
                Country("ME", "Montenegro", "+382", R.drawable.f135),
                Country("MS", "Montserrat", "+1664", R.drawable.f136),
                Country("MA", "Morocco", "+212", R.drawable.f137),
                Country("MZ", "Mozambique", "+258", R.drawable.f138),
                Country("MM", "Myanmar", "+95", R.drawable.f139),
                Country("NA", "Namibia", "+264", R.drawable.f140),
                Country("NR", "Nauru", "+674", R.drawable.f141),
                Country("NP", "Nepal", "+977", R.drawable.f142),
                Country("NL", "Netherlands", "+31", R.drawable.f143),
                Country("NC", "New Caledonia", "+687", R.drawable.f144),
                Country("NZ", "New Zealand", "+64", R.drawable.f145),
                Country("NI", "Nicaragua", "+505", R.drawable.f146),
                Country("NE", "Niger", "+227", R.drawable.f147),
                Country("NG", "Nigeria", "+234", R.drawable.f148),
                Country("NU", "Niue", "+683", R.drawable.f149),
                Country("NF", "Norfolk Island", "+672", R.drawable.f150),
                Country("KP", "North Korea", "+850", R.drawable.f151),
                Country("MP", "Northern Mariana Islands", "+1670", R.drawable.f152),
                Country("NO", "Norway", "+47", R.drawable.f153),
                Country("OM", "Oman", "+968", R.drawable.f154),
                Country("PK", "Pakistan", "+92", R.drawable.f155),
                Country("PW", "Palau", "+680", R.drawable.f156),
                Country("PS", "Palestine", "+970", R.drawable.f157),
                Country("PA", "Panama", "+507", R.drawable.f158),
                Country("PG", "Papua New Guinea", "+675", R.drawable.f159),
                Country("PY", "Paraguay", "+595", R.drawable.f160),
                Country("PE", "Peru", "+51", R.drawable.f161),
                Country("PH", "Philippines", "+63", R.drawable.f162),
                Country("PL", "Poland", "+48", R.drawable.f163),
                Country("PT", "Portugal", "+351", R.drawable.f164),
                Country("PR", "Puerto Rico", "+1", R.drawable.f165),
                Country("QA", "Qatar", "+974", R.drawable.f166),
                Country("RE", "Réunion", "+262", R.drawable.f167),
                Country("RO", "Romania", "+40", R.drawable.f168),
                Country("RU", "Russia", "+7", R.drawable.f169),
                Country("RW", "Rwanda", "+250", R.drawable.f170),
                Country("BL", "Saint Barthélemy", "+590", R.drawable.f171),
                Country("SH", "Saint Helena", "+290", R.drawable.f172),
                Country("KN", "Saint Kitts and Nevis", "+1869", R.drawable.f173),
                Country("LC", "Saint Lucia", "+1758", R.drawable.f174),
                Country("MF", "Saint Martin", "+590", R.drawable.f175),
                Country("PM", "Saint Pierre and Miquelon", "+508", R.drawable.f176),
                Country("VC", "Saint Vincent and the Grenadines", "+1784", R.drawable.f177),
                Country("WS", "Samoa", "+685", R.drawable.f178),
                Country("SM", "San Marino", "+378", R.drawable.f179),
                Country("ST", "São Tomé and Príncipe", "+239", R.drawable.f180),
                Country("SA", "Saudi Arabia", "+966", R.drawable.f181),
                Country("SN", "Senegal", "+221", R.drawable.f182),
                Country("RS", "Serbia", "+381", R.drawable.f183),
                Country("SC", "Seychelles", "+248", R.drawable.f184),
                Country("SL", "Sierra Leone", "+232", R.drawable.f185),
                Country("SG", "Singapore", "+65", R.drawable.f186),
                Country("SX", "Sint Maarten", "+1721", R.drawable.f187),
                Country("SK", "Slovakia", "+421", R.drawable.f188),
                Country("SI", "Slovenia", "+386", R.drawable.f189),
                Country("SB", "Solomon Islands", "+677", R.drawable.f190),
                Country("SO", "Somalia", "+252", R.drawable.f191),
                Country("ZA", "South Africa", "+27", R.drawable.f192),
                Country("KR", "South Korea", "+82", R.drawable.f193),
                Country("SS", "South Sudan", "+211", R.drawable.f194),
                Country("ES", "Spain", "+34", R.drawable.f195),
                Country("LK", "Sri Lanka", "+94", R.drawable.f196),
                Country("SD", "Sudan", "+249", R.drawable.f197),
                Country("SR", "Suriname", "+597", R.drawable.f198),
                Country("SZ", "Swaziland", "+268", R.drawable.f199),
                Country("SE", "Sweden", "+46", R.drawable.f200),
                Country("CH", "Switzerland", "+41", R.drawable.f201),
                Country("SY", "Syria", "+963", R.drawable.f202),
                Country("TW", "Taiwan", "+886", R.drawable.f203),
                Country("TJ", "Tajikistan", "+992", R.drawable.f204),
                Country("TZ", "Tanzania", "+255", R.drawable.f205),
                Country("TH", "Thailand", "+66", R.drawable.f206),
                Country("TL", "Timor-Leste", "+670", R.drawable.f207),
                Country("TG", "Togo", "+228", R.drawable.f208),
                Country("TK", "Tokelau", "+690", R.drawable.f209),
                Country("TO", "Tonga", "+676", R.drawable.f210),
                Country("TT", "Trinidad and Tobago", "+1868", R.drawable.f211),
                Country("TN", "Tunisia", "+216", R.drawable.f212),
                Country("TR", "Turkey", "+90", R.drawable.f213),
                Country("TM", "Turkmenistan", "+993", R.drawable.f214),
                Country("TC", "Turks and Caicos Islands", "+1649", R.drawable.f215),
                Country("TV", "Tuvalu", "+688", R.drawable.f216),
                Country("VI", "U.S. Virgin Islands", "+1340", R.drawable.f217),
                Country("UG", "Uganda", "+256", R.drawable.f218),
                Country("UA", "Ukraine", "+380", R.drawable.f219),
                Country("AE", "United Arab Emirates", "+971", R.drawable.f220),
                Country("GB", "United Kingdom", "+44", R.drawable.f221),
                Country("US", "United States", "+1", R.drawable.f222),
                Country("UY", "Uruguay", "+598", R.drawable.f223),
                Country("UZ", "Uzbekistan", "+998", R.drawable.f224),
                Country("VU", "Vanuatu", "+678", R.drawable.f225),
                Country("VA", "Vatican City", "+39", R.drawable.f226),
                Country("VE", "Venezuela", "+58", R.drawable.f227),
                Country("VN", "Vietnam", "+84", R.drawable.f228),
                Country("WF", "Wallis and Futuna", "+681", R.drawable.f229),
                Country("YE", "Yemen", "+967", R.drawable.f230),
                Country("ZM", "Zambia", "+260", R.drawable.f231),
                Country("ZW", "Zimbabwe", "+263", R.drawable.f232)
            )
        }
    }

    class NameComparator : Comparator<Country> {
        override fun compare(country: Country, t1: Country): Int {
            return country.name!!.compareTo(t1.name!!)
        }
    }

    class ISOCodeComparator : Comparator<Country> {
        override fun compare(country: Country, t1: Country): Int {
            return country.code!!.compareTo(t1.code!!)
        }
    }
}