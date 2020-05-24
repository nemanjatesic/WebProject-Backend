package mvcrest.avioni;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AvionskaKarta implements Serializable {
    private int id;
    private boolean one_way;
    private Let flight;
    private Date depart_date;
    private Date return_date;
    private AvionskaKompanija avionskaKompanija;
    private int available_count;
    private int version;

    public AvionskaKarta() {
    }

    public AvionskaKarta(int id, boolean one_way, Let flight, Date depart_date, Date return_date, AvionskaKompanija avionskaKompanija, int available_count, int version) {
        this.id = id;
        this.one_way = one_way;
        this.flight = flight;
        this.depart_date = depart_date;
        this.return_date = return_date;
        this.avionskaKompanija = avionskaKompanija;
        this.version = version;
        this.available_count = available_count;
    }

    public AvionskaKarta available_count(int available_count) {
        this.available_count = available_count;
        return this;
    }

    public AvionskaKarta id(int id){
        this.id = id;
        return this;
    }

    public AvionskaKarta one_way(boolean one_way){
        this.one_way = one_way;
        return this;
    }

    public AvionskaKarta flight(Let fligh){
        this.flight = fligh;
        return this;
    }

    public AvionskaKarta depart_date(Date depart_date){
        this.depart_date = depart_date;
        return this;
    }

    public AvionskaKarta return_date(Date return_date){
        this.return_date = return_date;
        return this;
    }

    public AvionskaKarta avionskaKompanija(AvionskaKompanija avionskaKompanija){
        this.avionskaKompanija = avionskaKompanija;
        return this;
    }

    public AvionskaKarta version(int version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvionskaKarta)) return false;
        AvionskaKarta that = (AvionskaKarta) o;
        return one_way == that.one_way &&
                available_count == that.available_count &&
                Objects.equals(flight, that.flight) &&
                Objects.equals(depart_date, that.depart_date) &&
                Objects.equals(return_date, that.return_date) &&
                Objects.equals(avionskaKompanija, that.avionskaKompanija);
    }

    @Override
    public int hashCode() {
        return Objects.hash(one_way, flight, depart_date, return_date, avionskaKompanija, available_count);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOne_way() {
        return one_way;
    }

    public void setOne_way(boolean one_way) {
        this.one_way = one_way;
    }

    public Let getFlight() {
        return flight;
    }

    public void setFlight(Let flight) {
        this.flight = flight;
    }

    public Date getDepart_date() {
        return depart_date;
    }

    public void setDepart_date(Date depart_date) {
        this.depart_date = depart_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public AvionskaKompanija getAvionskaKompanija() {
        return avionskaKompanija;
    }

    public void setAvionskaKompanija(AvionskaKompanija avionskaKompanija) {
        this.avionskaKompanija = avionskaKompanija;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    @Override
    public String toString() {
        return "AvionskaKarta{" +
                "id=" + id +
                ", one_way=" + one_way +
                ", fligh=" + flight +
                ", depart_date=" + depart_date +
                ", return_date=" + return_date +
                ", avionskaKompanija=" + avionskaKompanija +
                ", available_count=" + available_count +
                ", version=" + version +
                '}';
    }
}
