package com.alatheer.menu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Govern {

    @SerializedName("govern_id_pk")
    @Expose
    private String governIdPk;
    @SerializedName("govern_name")
    @Expose
    private String governName;
    @SerializedName("country_id_fk")
    @Expose
    private String countryIdFk;
    @SerializedName("country_id_pk")
    @Expose
    private String countryIdPk;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("currency_id_fk")
    @Expose
    private String currencyIdFk;
    @SerializedName("country_flag")
    @Expose
    private String countryFlag;
    @SerializedName("cities")
    @Expose
    private ArrayList<City> cities ;

    public String getGovernIdPk() {
        return governIdPk;
    }

    public void setGovernIdPk(String governIdPk) {
        this.governIdPk = governIdPk;
    }

    public String getGovernName() {
        return governName;
    }

    public void setGovernName(String governName) {
        this.governName = governName;
    }

    public String getCountryIdFk() {
        return countryIdFk;
    }

    public void setCountryIdFk(String countryIdFk) {
        this.countryIdFk = countryIdFk;
    }

    public String getCountryIdPk() {
        return countryIdPk;
    }

    public void setCountryIdPk(String countryIdPk) {
        this.countryIdPk = countryIdPk;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyIdFk() {
        return currencyIdFk;
    }

    public void setCurrencyIdFk(String currencyIdFk) {
        this.currencyIdFk = currencyIdFk;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

}