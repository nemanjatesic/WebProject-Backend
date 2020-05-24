package mvcrest.avioni;

import java.util.Date;

public class Filter {
    private String mestoPolaska;
    private String destinacija;
    private Date datumPolaska;
    private Date datumPovratka;
    private Boolean oneWay;

    public Filter() {
    }

    public Filter(String mestoPolaska, String destinacija, Date datumPolaska, Date datumPovratka) {
        this.mestoPolaska = mestoPolaska;
        this.destinacija = destinacija;
        this.datumPolaska = datumPolaska;
        this.datumPovratka = datumPovratka;
    }

    public Filter mestoPolaska(String mestoPolaska) {
        this.mestoPolaska = mestoPolaska;
        return this;
    }

    public Filter destinacija(String destinacija) {
        this.destinacija = destinacija;
        return this;
    }

    public Filter datumPolaska(Date datumPolaska) {
        this.datumPolaska = datumPolaska;
        return this;
    }

    public Filter datumPovratka(Date datumPovratka) {
        this.datumPovratka = datumPovratka;
        return this;
    }

    public Filter oneWay(Boolean oneWay) {
        this.oneWay = oneWay;
        return this;
    }

    public String getMestoPolaska() {
        return mestoPolaska;
    }

    public void setMestoPolaska(String mestoPolaska) {
        this.mestoPolaska = mestoPolaska;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public Date getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(Date datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public Date getDatumPovratka() {
        return datumPovratka;
    }

    public void setDatumPovratka(Date datumPovratka) {
        this.datumPovratka = datumPovratka;
    }

    public Boolean getOneWay() {
        return oneWay;
    }

    public void setOneWay(Boolean oneWay) {
        this.oneWay = oneWay;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "mestoPolaska='" + mestoPolaska + '\'' +
                ", destinacija='" + destinacija + '\'' +
                ", datumPolaska=" + datumPolaska +
                ", datumPovratka=" + datumPovratka +
                ", oneWay=" + oneWay +
                '}';
    }
}
