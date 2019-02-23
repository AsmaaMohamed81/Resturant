package com.alatheer.menu.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("city_id_pk")
    @Expose
    private String cityIdPk;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("govern_id_fk")
    @Expose
    private String governIdFk;
    @SerializedName("country_id_fk")
    @Expose
    private String countryIdFk;

    public String getCityIdPk() {
        return cityIdPk;
    }

    public void setCityIdPk(String cityIdPk) {
        this.cityIdPk = cityIdPk;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getGovernIdFk() {
        return governIdFk;
    }

    public void setGovernIdFk(String governIdFk) {
        this.governIdFk = governIdFk;
    }

    public String getCountryIdFk() {
        return countryIdFk;
    }

    public void setCountryIdFk(String countryIdFk) {
        this.countryIdFk = countryIdFk;
    }

}