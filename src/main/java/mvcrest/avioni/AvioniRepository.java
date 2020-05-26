package mvcrest.avioni;

import mvcrest.database.Database;

import javax.xml.crypto.Data;
import java.util.List;

public class AvioniRepository {

    public static List<AvionskaKompanija> getKompanije(){
        return Database.getInstance().getAllCompanies();
    }

    public static List<AvionskaKarta> getKarte(){
        return Database.getInstance().getAllCards();
    }

    public static List<AvionskaKarta> filter(Filter filter) {
        return Database.getInstance().filter(filter);
    }

    public static AvionskaKarta addKarta(AvionskaKarta avionskaKarta){
        Database.getInstance().addCard(avionskaKarta);
        return avionskaKarta;
    }

    public static int addRezervacija(Rezervacija rezervacija){
        return Database.getInstance().addRezervacija(rezervacija);
    }

    public static Korisnik getKorisnikByUsernameAndPassword(String username, String password) {
        return Database.getInstance().getKorisnikByUsernameAndPassword(username, password);
    }

    public static List<Let> getLetovi() {
        return Database.getInstance().getAllFlights();
    }

    public static Korisnik createKorisnik(Korisnik korisnik) {
        Database.getInstance().addKorisnik(korisnik);
        return korisnik;
    }

    public static Korisnik getKorisnikByUsername(String username) {
        return Database.getInstance().getKorisnikByUsername(username);
    }

    public static AvionskaKarta getKartaByID(int ID) {
        return Database.getInstance().getAvionskaKartaByID(ID);
    }

    public static boolean deleteKartaByID(int ID) {
        return Database.getInstance().deleteAvionskaKartaByID(ID);
    }

    public static boolean deleteCompanyByID(int ID) {
        return Database.getInstance().deleteCompanyByID(ID);
    }

    public static boolean modifyKarta(AvionskaKarta avionskaKarta) {
        return Database.getInstance().modifyKarta(avionskaKarta);
    }

    public static List<Rezervacija> getRezervacijeForUsername(String username) {
        return Database.getInstance().getRezervacijeByUsername(username);
    }

    public static List<Rezervacija> filterRezervacijeForUsername(String username, Filter filter) {
        return Database.getInstance().filterRezervacijeForUsername(username, filter);
    }

    public static boolean deleteRezervacijaByID(int ID) {
        return Database.getInstance().deleteRezervacijaByID(ID);
    }

    public static AvionskaKompanija getKompanijaById(int ID) {
        return Database.getInstance().getAvionskaKompanijaByID(ID);
    }

    public static List<AvionskaKarta> getCardsForCompanyId(int ID) {
        return Database.getInstance().getCardsForCompanyID(ID);
    }

    public static boolean createCompany(AvionskaKompanija avionskaKompanija) {
        return Database.getInstance().addCompany(avionskaKompanija);
    }

    public static boolean changeCompanyName(AvionskaKompanija avionskaKompanija, String newName){
        return Database.getInstance().changeCompanyName(avionskaKompanija, newName);
    }
}
