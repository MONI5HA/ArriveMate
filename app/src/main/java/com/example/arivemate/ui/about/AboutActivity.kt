package com.example.arivemate.ui.about

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityAboutBinding
import com.example.arivemate.viewmodel.CountryViewModel

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)

        val countryName = "canada"
        val wikiTitle = "History_of_Canada"

        binding.textCountryName.text = countryName

        viewModel.fetchCountryInfo(countryName, wikiTitle)

        viewModel.countryData.observe(this) { data ->
            binding.valueOfficialName.text = data.name.official
            binding.valueCapital.text = data.capital.firstOrNull() ?: "N/A"
            binding.valueRegion.text = data.region
            binding.textFlagDescription.text=data.flags.alt
            binding.valueSubregion.text = data.subregion
            binding.valueContinent.text = data.continents.firstOrNull() ?: "N/A"
            binding.valuePopulation.text = data.population.toString()

            val currency = data.currencies.entries.firstOrNull()?.value
            binding.valueCurrency.text = "${currency?.name} (${currency?.symbol})"

            val languages = data.languages.values.joinToString(", ")
            binding.valueLanguages.text = languages

            Glide.with(this)
                .load(data.flags.png)
                .into(binding.imageFlag)
        }

        viewModel.countryHistory.observe(this) {
            binding.textHistory.text = it
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}
