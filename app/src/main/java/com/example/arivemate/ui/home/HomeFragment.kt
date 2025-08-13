package com.example.arivemate.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.arivemate.R
import com.example.arivemate.databinding.FragmentHomeBinding
import com.example.arivemate.retrofit.currency.CountryCurrencyMap
import com.example.arivemate.ui.about.AboutActivity
import com.example.arivemate.ui.contactInformation.ContactActivity
import com.example.arivemate.ui.news.NewsActivity
import com.example.arivemate.ui.student_center.country
import com.example.arivemate.ui.translate.Translate
import com.example.arivemate.viewmodel.CurrencyViewModel
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CurrencyViewModel by viewModels()

    private var selectedCountryCode:String="Ca"

    private var selectedCountryName: String="Canada"
    private var fromCurrency: String = "USD"
    private var toCurrency: String = "INR"

    private var debounceJob: Job? = null
    private val debounceDelay = 400L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        //-----------------------------Top Country change -----------------------------------
        binding.countryDisplay.text = binding.countryCodePicker.selectedCountryName

        binding.countryCodePicker.setOnCountryChangeListener {
            selectedCountryName = binding.countryCodePicker.selectedCountryName
            selectedCountryCode = binding.countryCodePicker.selectedCountryNameCode
            val flagResId = binding.countryCodePicker.selectedCountryFlagResourceId
            val drawable = ContextCompat.getDrawable(requireContext(), flagResId)

            binding.countryDisplay.text = selectedCountryName
            binding.countryImg.setImageDrawable(drawable)
        }

        //--------------------------------Currency Converter --------------------------------------------------------------------------------
        // Initial setup
        fromCurrency = CountryCurrencyMap.countryToCurrency[binding.countryCodePickerfrom.selectedCountryName] ?: "USD"
        toCurrency = CountryCurrencyMap.countryToCurrency[binding.countryCodePickerto.selectedCountryName] ?: "INR"

        // Listener for FROM country
        binding.countryCodePickerfrom.setOnCountryChangeListener {
            val country = binding.countryCodePickerfrom.selectedCountryName
            fromCurrency = CountryCurrencyMap.countryToCurrency[country] ?: "USD"
            triggerConversion()
        }

        // Listener for TO country
        binding.countryCodePickerto.setOnCountryChangeListener {
            val country = binding.countryCodePickerto.selectedCountryName
            toCurrency = CountryCurrencyMap.countryToCurrency[country] ?: "INR"
            triggerConversion()
        }

        // Amount input listener
        binding.currencyAmtEntered.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = triggerConversion()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Observe API responses
        viewModel.rates.observe(viewLifecycleOwner) { rateMap ->
            val amount = binding.currencyAmtEntered.text.toString().toDoubleOrNull()
            if (amount != null && rateMap != null) {
                val rate = rateMap[toCurrency] ?: 0.0
                val converted = rate * amount
                binding.currencyAmtConverted.text = "Converted: $ %.2f".format(converted)
            } else {
                binding.currencyAmtConverted.text = "Converted: $ 0.00"
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        //-------------------------------News ----------------------------------------------------

        binding.newLayout.setOnClickListener {
            val intent = Intent(requireContext(),NewsActivity::class.java);
            intent.putExtra("country_name",selectedCountryCode)
            startActivity(intent)

        }

        binding.newLayoutText.setOnClickListener {
            val intent = Intent(requireContext(),NewsActivity::class.java);
            intent.putExtra("country_name",selectedCountryCode)
            startActivity(intent)
        }

        //----------------------------About_Section -------------------------------------
        binding.contactSection.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java);
            intent.putExtra("country_name",selectedCountryName)
            startActivity(intent)
        }
        binding.abouttext.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java);
            intent.putExtra("country_name",selectedCountryName)
            startActivity(intent)
        }


        //----------------------------Contact_Section -------------------------------------
        binding.aboutSection.setOnClickListener {
            val intent = Intent(requireContext(), ContactActivity::class.java);
            intent.putExtra("country_name",selectedCountryName)
            startActivity(intent)
        }
        binding.ContactInformation.setOnClickListener {
            val intent = Intent(requireContext(), ContactActivity::class.java);
            intent.putExtra("country_name",selectedCountryName)
            startActivity(intent)
        }
        //----------------------------Translate_Section -------------------------------------
        binding.translateSection.setOnClickListener {
                    val intent = Intent(requireContext(), Translate::class.java);
                    intent.putExtra("country_name",selectedCountryName)
                    startActivity(intent)
        }
        binding.transtaleText.setOnClickListener {
            val intent = Intent(requireContext(), Translate::class.java);
            intent.putExtra("country_name",selectedCountryName)
            startActivity(intent)
        }

        //-------------------------- Student Center ---------------------------------------
        binding.studentCenter.setOnClickListener {
            val intent = Intent(requireContext(), country::class.java)
            startActivity(intent);
        }




        return binding.root
    }










    private fun triggerConversion() {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(debounceDelay)
            val amountText = binding.currencyAmtEntered.text.toString()
            if (amountText.isNotBlank() && amountText.toDoubleOrNull() != null) {
                viewModel.fetchRates(fromCurrency)
            }
        }
    }
}
