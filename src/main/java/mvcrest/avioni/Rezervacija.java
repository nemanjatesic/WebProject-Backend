package mvcrest.avioni;

import java.util.Objects;

public class Rezervacija {
    private int id;
    private boolean isAvailable;
    private Let fligh;
    private AvionskaKarta avionskaKarta;
    private Korisnik korisnik;
    private int version;

    public Rezervacija() {
    }

    public Rezervacija(int id, boolean isAvailable, Let fligh, AvionskaKarta avionskaKarta, Korisnik korisnik, int version) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.fligh = fligh;
        this.avionskaKarta = avionskaKarta;
        this.korisnik = korisnik;
        this.version = version;
    }

    public Rezervacija id(int id) {
        this.id = id;
        return this;
    }

    public Rezervacija isAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public Rezervacija fligh(Let fligh) {
        this.fligh = fligh;
        return this;
    }

    public Rezervacija avionskaKarta(AvionskaKarta avionskaKarta) {
        this.avionskaKarta = avionskaKarta;
        return this;
    }

    public Rezervacija korisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
        return this;
    }

    public Rezervacija version(int version) {
        this.version = version;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Let getFligh() {
        return fligh;
    }

    public void setFligh(Let fligh) {
        this.fligh = fligh;
    }

    public AvionskaKarta getAvionskaKarta() {
        return avionskaKarta;
    }

    public void setAvionskaKarta(AvionskaKarta avionskaKarta) {
        this.avionskaKarta = avionskaKarta;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rezervacija)) return false;
        Rezervacija that = (Rezervacija) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "id=" + id +
                ", isAvailable=" + isAvailable +
                ", fligh=" + fligh +
                ", avionskaKarta=" + avionskaKarta +
                ", korisnik=" + korisnik +
                ", version=" + version +
                '}';
    }
}
