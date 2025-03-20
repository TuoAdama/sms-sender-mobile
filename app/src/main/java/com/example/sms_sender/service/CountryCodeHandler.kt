package com.example.sms_sender.service

object CountryCodeHandler {

    const val FRANCE: String = "France";

    var countriesMap = HashMap<String, Int>();

    init {
        countriesMap["France"] = 33;
    }


    fun getCountries(): List<String>{
        return countriesMap.keys.toList();
    }

    fun getCode(countryName: String): Int?{
        return countriesMap[countryName];
    }
}