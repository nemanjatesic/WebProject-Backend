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

    public boolean modifyKarta(AvionskaKarta avionskaKarta) {
        return AvioniRepository.modifyKarta(avionskaKarta);
    }
}
