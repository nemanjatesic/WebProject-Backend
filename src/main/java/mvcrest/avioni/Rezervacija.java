package mvcrest.avioni;

import java.util.Objects;

public class Rezervacija {
    private int id;
    private boolean available;
    private Let flight;
    private AvionskaKarta avionskaKarta;
    private Korisnik korisnik;
    private int version;

    public Rezervacija() {
    }

    public Rezervacija(int id, boolean available, Let flight, AvionskaKarta avionskaKarta, Korisnik korisnik, int version) {
        this.id = id;
        this.available = available;
        this.flight = flight;
        this.avionskaKarta = avionskaKarta;
        this.korisnik = korisnik;
        this.version = version;
    }

    public Rezervacija id(int id) {
        this.id = id;
        return this;
    }

    public Rezervacija available(boolean available) {
        this.available = available;
        return this;
    }

    public Rezervacija flight(Let flight) {
        this.flight = flight;
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
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Let getFlight() {
        return flight;
    }

    public void setFlight(Let flight) {
        this.flight = flight;
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
        return Objects.equals(flight.getId(), that.flight.getId()) &&
                Objects.equals(avionskaKarta.getId(), that.avionskaKarta.getId()) &&
                Objects.equals(korisnik.getId(), that.korisnik.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, avionskaKarta, korisnik);
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "id=" + id +
                ", isAvailable=" + available +
                ", fligh=" + flight +
                ", avionskaKarta=" + avionskaKarta +
                ", korisnik=" + korisnik +
                ", version=" + version +
                '}';
    }
}
