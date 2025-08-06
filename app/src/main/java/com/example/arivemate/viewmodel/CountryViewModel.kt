package com.example.arivemate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arivemate.data.model.CountryResponse
import com.example.arivemate.data.model.WikiPage
import com.example.arivemate.data.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private val repository = CountryRepository()

    val countryData = MutableLiveData<CountryResponse>()
    val countryHistory = MutableLiveData<String>()

    val error = MutableLiveData<String>()

    fun fetchCountryInfo(name: String, wikiTitle: String) {
        viewModelScope.launch {
            try {
                val countryList = repository.getCountryDetails(name)
                countryData.postValue(countryList[0])

                val history = repository.getCountryHistory(wikiTitle)
                val extract = history.query.pages.values.firstOrNull()?.extract ?: "No history found"
                countryHistory.postValue(extract)

            } catch (e: Exception) {
                error.postValue("Failed: ${e.message}")
            }
        }
    }
}
