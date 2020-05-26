package mvcrest.avioni;

import java.util.List;

public class AvioniService {

    public List<AvionskaKompanija> getKompanije() {
        return AvioniRepository.getKompanije();
    }

    public List<AvionskaKarta> getKarte() {
        return AvioniRepository.getKarte();
    }

    public List<AvionskaKarta> filter(Filter filter) {
        return AvioniRepository.filter(filter);
    }

    public AvionskaKarta addKarta(AvionskaKarta avionskaKarta) {
        return AvioniRepository.addKarta(avionskaKarta);
    }

    public int addRezervacija(Rezervacija rezervacija) {
        return AvioniRepository.addRezervacija(rezervacija);
    }

    public Korisnik getKorisnikByUsernameAndPassword(String username, String password) {
        return AvioniRepository.getKorisnikByUsernameAndPassword(username, password);
    }

    public List<Let> getLetovi() {
        return AvioniRepository.getLetovi();
    }

    public Korisnik createKorisnik(Korisnik korisnik) {
        return AvioniRepository.createKorisnik(korisnik);
    }

    public AvionskaKarta getKartaByID(int ID) {
        return AvioniRepository.getKartaByID(ID);
    }

    public boolean deleteKartaByID(int ID) {
        return AvioniRepository.deleteKartaByID(ID);
    }

    public boolean deleteCompanyByID(int ID) {
        return AvioniRepository.deleteCompanyByID(ID);
    }

    public boolean modifyKarta(AvionskaKarta avionskaKarta) {
        return AvioniRepository.modifyKarta(avionskaKarta);
    }

    public List<Rezervacija> getRezervacijeForUsername(String username) {
        return AvioniRepository.getRezervacijeForUsername(username);
    }

    public List<Rezervacija> filterRezervacijeForUsername(String username, Filter filter) {
        return AvioniRepository.filterRezervacijeForUsername(username, filter);
    }

    public boolean deleteRezervacijaByID(int ID) {
        return AvioniRepository.deleteRezervacijaByID(ID);
    }

    public AvionskaKompanija getKompanijaById(int ID) {
        return AvioniRepository.getKompanijaById(ID);
    }

    public List<AvionskaKarta> getCardsForCompanyId(int ID) {
        return AvioniRepository.getCardsForCompanyId(ID);
    }

    public boolean createCompany(AvionskaKompanija avionskaKompanija) {
        return AvioniRepository.createCompany(avionskaKompanija);
    }

    public boolean changeCompanyName(AvionskaKompanija avionskaKompanija, String newName) {
        return AvioniRepository.changeCompanyName(avionskaKompanija, newName);
    }
}
