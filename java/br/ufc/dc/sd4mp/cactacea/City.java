package br.ufc.dc.sd4mp.cactacea;

/**
 * Created by Lucas on 24/05/2015.
 */

public class City {
    private int id;

    private String nameCity;

    private String UF;

    private int population;

    private double rateHomicides;

    private int nacionalPosition;

    private Double lat;

    private Double lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getRateHomicides() {
        return rateHomicides;
    }

    public void setRateHomicides(double rateHomicides) {
        this.rateHomicides = rateHomicides;
    }

    public int getNacionalPosition() {
        return nacionalPosition;
    }

    public void setNacionalPosition(int nacionalPosition) {
        this.nacionalPosition = nacionalPosition;
    }

    public String toString2(){
        return nameCity + " " + UF + " " + population + "\n" +
               rateHomicides + " - " + nacionalPosition + "\n";
    }

    public String toString(){
        return ""+nameCity;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}

