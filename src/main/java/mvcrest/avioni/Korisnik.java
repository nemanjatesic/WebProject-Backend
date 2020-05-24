package mvcrest.avioni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Korisnik implements Serializable {
    private int id;
    private String username;
    private String password;
    private TipKorisnika tipKorisnika;
    private List<Rezervacija> rezervacije;
    private int version;
    private String JWTToken;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Korisnik() {
        rezervacije = new ArrayList<>();
    }

    public Korisnik(int id, String username, String password, TipKorisnika tipKorisnika, List<Rezervacija> rezervacije, int version) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipKorisnika = tipKorisnika;
        this.rezervacije = rezervacije;
        this.version = version;
    }

    public Korisnik JWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
        return this;
    }

    public Korisnik id(int id) {
        this.id = id;
        return this;
    }

    public Korisnik username(String username) {
        this.username = username;
        return this;
    }

    public Korisnik password(String password) {
        this.password = password;
        return this;
    }

    public Korisnik tipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
        return this;
    }

    public Korisnik rezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Korisnik)) return false;
        Korisnik korisnik = (Korisnik) o;
        return id == korisnik.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Korisnik version(int version) {
        this.version = version;
        return this;
    }

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipKorisnika getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = Objects.requireNonNullElseGet(rezervacije, ArrayList::new);
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tipKorisnika=" + tipKorisnika +
                ", rezervacije=" + rezervacije +
                ", version=" + version +
                '}';
    }
}
