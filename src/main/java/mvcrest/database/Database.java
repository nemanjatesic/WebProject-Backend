package mvcrest.database;

import mvcrest.avioni.*;
import mvcrest.util.Util;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class Database {
    private Connection c = null;
    private static Database instance = null;

    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_projekat", "root", "root");
            System.out.println("Connected to DB");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }


    public int addCard(AvionskaKarta avionskaKarta) {
        try {
            if (!validateKarta(avionskaKarta)) return 409;
            String query = "INSERT INTO avionska_karta (id, one_way, depart_date, return_date, available_count, version, let_id, avionska_kompanija_id)"
                    + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setBoolean(1, avionskaKarta.isOne_way());
            preparedStmt.setDate(2, Date.valueOf(avionskaKarta.getDepart_date().toInstant().atZone(ZoneId.of("UTC")).toLocalDate()));
            if (avionskaKarta.getReturn_date() != null)
                preparedStmt.setDate(3, Date.valueOf(avionskaKarta.getReturn_date().toInstant().atZone(ZoneId.of("UTC")).toLocalDate()));
            else
                preparedStmt.setDate(3, null);
            preparedStmt.setInt(4, avionskaKarta.getAvailable_count());
            preparedStmt.setInt(5, avionskaKarta.getVersion());
            preparedStmt.setInt(6, getFlightByFlight(avionskaKarta.getFlight()).getId());
            preparedStmt.setInt(7, getAvionskaKompanijaIDByName(avionskaKarta.getAvionskaKompanija().getName()));
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public int addCompany(AvionskaKompanija avionskaKompanija) {
        if (getAllCompanies().contains(avionskaKompanija)) return 409;
        if (!validateCompany(avionskaKompanija)) return 409;
        try {
            String query = "INSERT INTO avionska_kompanija (id, name, version)"
                    + " VALUES (DEFAULT, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString(1, avionskaKompanija.getName());
            preparedStmt.setInt(2, avionskaKompanija.getVersion());
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public int addGrad(Grad grad) {
        if (getAllGradovi().contains(grad)) return 409;
        if (!validateCity(grad)) return 409;
        try {
            String query = "INSERT INTO grad (id, name, version)"
                    + " VALUES (DEFAULT, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString(1, grad.getName());
            preparedStmt.setInt(2, grad.getVersion());
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public int addKorisnik(Korisnik korisnik) {
        if (!validateUser(korisnik)) {
            return 409;
        }
        for (Korisnik korisnik1 : getAllKorisnici()) {
            if (korisnik1.getUsername().equals(korisnik.getUsername())) {
                return 409;
            }
        }
        try {
            String query = "INSERT INTO korisnik (id, username, password, tip_korisnika, version)"
                    + " VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setString(1, korisnik.getUsername());
            preparedStmt.setString(2, korisnik.getPassword());
            preparedStmt.setString(3, korisnik.getTipKorisnika().toString());
            preparedStmt.setInt(4, korisnik.getVersion());
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public int addRezervacija(Rezervacija rezervacija) {
        try {
            if (rezervacija.getAvionskaKarta().getVersion() != getAvionskaKartaByID(rezervacija.getAvionskaKarta().getId()).getVersion())
                return 410;
            if (getAllRezervacije().contains(rezervacija))
                return 409;
            AvionskaKarta ak = getAvionskaKartaByID(rezervacija.getAvionskaKarta().getId());
            if (ak.getAvailable_count() <= 0)
                return 408;
            ak.setAvailable_count(ak.getAvailable_count() - 1);
            if (!modifyKarta(ak))
                return 408;
            String query = "INSERT INTO rezervacija (id, isAvailable,version, let_id, avionska_karta_id, korisnik_id)"
                    + " VALUES (DEFAULT, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setBoolean(1, rezervacija.isAvailable());
            preparedStmt.setInt(2, rezervacija.getVersion());
            preparedStmt.setInt(3, rezervacija.getFlight().getId());
            preparedStmt.setInt(4, rezervacija.getAvionskaKarta().getId());
            preparedStmt.setInt(5, rezervacija.getKorisnik().getId());
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public int addFlight(Let let) {
        if (getAllFlights().contains(let)) return 409;
        if (!validateFlight(let)) return 409;
        try {
            String query = "INSERT INTO let (id, version, origin_grad_id, destination_grad_id)"
                    + " VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement preparedStmt = c.prepareStatement(query);
            preparedStmt.setInt(1, let.getVersion());
            preparedStmt.setInt(2, getGradIDByName(let.getGrad_origin().getName()));
            preparedStmt.setInt(3, getGradIDByName(let.getGrad_destination().getName()));
            preparedStmt.execute();
            return 200;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 500;
    }

    public boolean deleteAvionskaKartaByID(int id) {
        try {
            if (!deleteRezervacijeByAvionskaKartaID(id))
                return false;

            String sql = "DELETE FROM avionska_karta WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRezervacijeByAvionskaKartaID(int id) {
        try {
            String sql = "DELETE FROM rezervacija WHERE avionska_karta_id=" + id + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRezervacijaByID(int id) {
        try {
            Rezervacija rezervacija = getRezervacijaByID(id);
            if (rezervacija == null)
                return false;
            int korisnikID = rezervacija.getKorisnik().getId();
            AvionskaKarta avionskaKarta = getAvionskaKartaByID(rezervacija.getAvionskaKarta().getId());
            avionskaKarta.setAvailable_count(avionskaKarta.getAvailable_count() + 1);
            if (!modifyKarta(avionskaKarta))
                return false;
            String sql = "DELETE FROM rezervacija WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);

            updateAllRezervacije(korisnikID);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateAllRezervacije(int korisnikID) {
        try {
            List<Rezervacija> rezervacije = getRezervacijeByKorisnikID(korisnikID);
            java.util.Date datum = new java.util.Date();
            for (Rezervacija rezervacija : rezervacije) {
                long currentTime = datum.getTime();
                long departTime = rezervacija.getAvionskaKarta().getDepart_date().getTime();
                String sql = "UPDATE rezervacija SET isAvailable=";

                if (departTime - currentTime >= 86400000L)
                    sql += 1 + " ";
                else
                    sql += 0 + " ";

                sql += "WHERE id=" + rezervacija.getId() + ";";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCompanyByID(int id) {
        try {
            if (!deleteAllCardsByComapnyID(id))
                return false;
            String sql = "DELETE FROM avionska_kompanija WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAllCardsByComapnyID(int id) {
        try {
            String sql = "DELETE FROM avionska_karta WHERE avionska_kompanija_id=" + id + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modifyKarta(AvionskaKarta avionskaKarta) {
        try {
            AvionskaKarta kartaInBase = getAvionskaKartaByID(avionskaKarta.getId());
            if (kartaInBase.getVersion() != avionskaKarta.getVersion())
                return false;
            String sql = "UPDATE avionska_karta SET "; //"WHERE username='" + "" + "';";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (avionskaKarta.isOne_way() != kartaInBase.isOne_way())
                sql += "one_way = " + (avionskaKarta.isOne_way() ? 1 : 0) + ", ";
            if (!avionskaKarta.getDepart_date().equals(kartaInBase.getDepart_date()))
                sql += "depart_date = '" + sdf.format(avionskaKarta.getDepart_date()) + "', ";
            if (!avionskaKarta.isOne_way() && !avionskaKarta.getReturn_date().equals(kartaInBase.getReturn_date()))
                sql += "return_date = '" + sdf.format(avionskaKarta.getReturn_date()) + "', ";
            if (avionskaKarta.getAvailable_count() != kartaInBase.getAvailable_count())
                sql += "available_count = " + avionskaKarta.getAvailable_count() + ", ";
            if (avionskaKarta.getFlight().getId() != kartaInBase.getFlight().getId())
                sql += "let_id = " + avionskaKarta.getFlight().getId() + ", ";
            if (avionskaKarta.getAvionskaKompanija().getId() != kartaInBase.getAvionskaKompanija().getId())
                sql += "avionska_kompanija_id = " + avionskaKarta.getAvionskaKompanija().getId() + ", ";
            if (kartaInBase.getReturn_date() != null && avionskaKarta.isOne_way())
                sql += "return_date = null , ";
            sql += "version = " + (avionskaKarta.getVersion() + 1) + " ";
            sql += "WHERE id = " + avionskaKarta.getId() + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeCompanyName(AvionskaKompanija avionskaKompanija, String newName) {
        try {
            for (AvionskaKompanija komp : getAllCompanies()) {
                if (komp.getId() == avionskaKompanija.getId()) {
                    if (komp.getName().equals(newName)) {
                        return false;
                    }
                    if (komp.getVersion() != avionskaKompanija.getVersion())
                        return false;
                    String sql = "UPDATE avionska_kompanija SET name = '" + newName + "' , version = " + (avionskaKompanija.getVersion() + 1) + " WHERE id =" + avionskaKompanija.getId() + ";";
                    Statement stmt = c.createStatement();
                    stmt.executeUpdate(sql);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Rezervacija> filterRezervacijeForUsername(String username, Filter filter) {
        try {
            var lista = getRezervacijeByUsername(username)
                    .stream()
                    .filter(rezervacija -> {
                        if (Util.isEmpty(filter.getMestoPolaska())) return true;
                        else return rezervacija.getFlight().getGrad_origin().getName().toLowerCase()
                                .contains(filter.getMestoPolaska().toLowerCase());
                    })
                    .filter(rezervacija -> {
                        if (Util.isEmpty(filter.getDestinacija())) return true;
                        else return rezervacija.getFlight().getGrad_destination().getName().toLowerCase()
                                .contains(filter.getDestinacija().toLowerCase());
                    })
                    .filter(rezervacija -> {
                        if (filter.getDatumPolaska() == null) return true;
                        if (rezervacija.getAvionskaKarta().getDepart_date() == null) return true;
                        else return filter.getDatumPolaska().equals(rezervacija.getAvionskaKarta().getDepart_date())
                                || filter.getDatumPolaska().before(rezervacija.getAvionskaKarta().getDepart_date());
                    })
                    .filter(rezervacija -> {
                        if (filter.getDatumPovratka() == null) return true;
                        if (rezervacija.getAvionskaKarta().getReturn_date() == null) return true;
                        else return filter.getDatumPovratka().equals(rezervacija.getAvionskaKarta().getReturn_date())
                                || filter.getDatumPovratka().after(rezervacija.getAvionskaKarta().getReturn_date());
                    })
                    .filter(rezervacija -> {
                        if (filter.getOneWay() == null) return true;
                        return filter.getOneWay() == rezervacija.getAvionskaKarta().isOne_way();
                    })
                    .collect(Collectors.toList());
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public AvionskaKarta getAvionskaKartaByAvionskaKarta(AvionskaKarta avionskaKarta) {
        try {
            List<AvionskaKarta> avionskeKarte = getAllCards();
            for (AvionskaKarta ak : avionskeKarte) {
                if (ak.equals(avionskaKarta)) {
                    return ak;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Let getFlightByFlight(Let let) {
        List<Let> flights = getAllFlights();
        return flights.get(flights.indexOf(let));
    }

    public Korisnik getKorisnikByUsername(String username) {
        try {
            String sql = "SELECT * FROM korisnik WHERE username='" + username + "';";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeKorisnik(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Korisnik getKorisnikByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM korisnik WHERE username='" + username + "' AND password='" + password + "';";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                Korisnik korisnik = makeKorisnik(rs);
                korisnik.setRezervacije(getRezervacijeByKorisnikID(rs.getInt("id")));
                return korisnik;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AvionskaKarta> filter(Filter filter) {
        try {
            var filtered = getAllCards()
                    .stream()
                    .filter(avionskaKarta -> {
                        if (Util.isEmpty(filter.getMestoPolaska())) return true;
                        else return avionskaKarta.getFlight().getGrad_origin().getName().toLowerCase()
                                .contains(filter.getMestoPolaska().toLowerCase());
                    })
                    .filter(avionskaKarta -> {
                        if (Util.isEmpty(filter.getDestinacija())) return true;
                        else return avionskaKarta.getFlight().getGrad_destination().getName().toLowerCase()
                                .contains(filter.getDestinacija().toLowerCase());
                    })
                    .filter(avionskaKarta -> {
                        if (filter.getDatumPolaska() == null) return true;
                        if (avionskaKarta.getDepart_date() == null) return true;
                        else return filter.getDatumPolaska().equals(avionskaKarta.getDepart_date())
                                || filter.getDatumPolaska().before(avionskaKarta.getDepart_date());
                    })
                    .filter(avionskaKarta -> {
                        if (filter.getDatumPovratka() == null) return true;
                        if (avionskaKarta.getReturn_date() == null) return true;
                        else return filter.getDatumPovratka().equals(avionskaKarta.getReturn_date())
                                || filter.getDatumPovratka().after(avionskaKarta.getReturn_date());
                    })
                    .filter(avionskaKarta -> {
                        if (filter.getOneWay() == null) return true;
                        return filter.getOneWay() == avionskaKarta.isOne_way();
                    })
                    .collect(Collectors.toList());
            return filtered;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int getAvionskaKompanijaIDByName(String name) {
        try {
            String sql = "SELECT * FROM avionska_kompanija WHERE name='" + name + "';";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("id");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGradIDByName(String name) {
        try {
            String sql = "SELECT * FROM grad WHERE name='" + name + "';";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("id");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getKorisnikIDByName(String name) {
        try {
            String sql = "SELECT * FROM korisnik WHERE username='" + name + "';";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("id");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Rezervacija> getRezervacijeByUsername(String username) {
        List<Rezervacija> rezervacije = new ArrayList<>();
        try {
            int korisnikID = getKorisnikIDByName(username);
            if (korisnikID == 0) return rezervacije;

            String sql = "SELECT * FROM rezervacija WHERE korisnik_id=" + korisnikID + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                rezervacije.add(makeRezervacija(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }



    /**********************************************************************************************************/
    /** START OF GET ALL **/

    public List<Grad> getAllGradovi() {
        List<Grad> gradovi = new ArrayList<>();
        try {
            String sql = "SELECT * FROM grad;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                gradovi.add(makeGrad(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return gradovi;
    }

    public List<Rezervacija> getAllRezervacije() {
        List<Rezervacija> rezervacije = new ArrayList<>();
        try {
            String sql = "SELECT * FROM rezervacija;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                rezervacije.add(makeRezervacija(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

    public List<Let> getAllFlights() {
        List<Let> letovi = new ArrayList<>();
        try {
            String sql = "SELECT * FROM let;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Let let = makeFlight(rs);
                let.setAvionskeKarte(getCardsForFlightID(rs.getInt("id")));
                letovi.add(let);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return letovi;
    }

    public List<Korisnik> getAllKorisnici() {
        List<Korisnik> korisnici = new ArrayList<>();
        try {
            String sql = "SELECT * FROM korisnik;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                korisnici.add(makeKorisnik(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return korisnici;
    }

    public List<AvionskaKarta> getAllCards() {
        List<AvionskaKarta> avionskaKarte = new ArrayList<>();
        try {
            String sql = "SELECT * FROM avionska_karta;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                AvionskaKarta avionskaKarta = makeAvionskaKarta(rs);
                avionskaKarta.getFlight().setAvionskeKarte(getCardsForFlightID(rs.getInt("let_id")));
                avionskaKarte.add(avionskaKarta);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return avionskaKarte;
    }

    public List<AvionskaKompanija> getAllCompanies() {
        List<AvionskaKompanija> avionskaKompanije = new ArrayList<>();
        try {
            String sql = "SELECT * FROM avionska_kompanija;";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                avionskaKompanije.add(makeAvionskaKompanija(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return avionskaKompanije;
    }

    /** END OF GET ALL **/
    /**********************************************************************************************************/

    public List<AvionskaKarta> getCardsForFlightID(int id) {
        List<AvionskaKarta> karte = new ArrayList<>();
        try {
            String sql = "SELECT * FROM avionska_karta WHERE let_id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                karte.add(makeAvionskaKarta(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return karte;
    }

    public List<AvionskaKarta> getCardsForCompanyID(int id) {
        List<AvionskaKarta> karte = new ArrayList<>();
        try {
            String sql = "SELECT * FROM avionska_karta WHERE avionska_kompanija_id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                karte.add(makeAvionskaKarta(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return karte;
    }

    public List<Rezervacija> getRezervacijeByKorisnikID(int id) {
        List<Rezervacija> rezervacije = new ArrayList<>();
        try {
            String sql = "SELECT * FROM rezervacija WHERE korisnik_id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                rezervacije.add(makeRezervacija(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

    /**********************************************************************************************************/
    /** START OF GET BY ID **/

    public AvionskaKompanija getAvionskaKompanijaByID(int id) {
        try {
            String sql = "SELECT * FROM avionska_kompanija WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeAvionskaKompanija(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AvionskaKarta getAvionskaKartaByID(int id) {
        try {
            String sql = "SELECT * FROM avionska_karta WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeAvionskaKarta(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Grad getGradByID(int id) {
        try {
            String sql = "SELECT * FROM grad WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeGrad(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Let getFlightByID(int id) {
        try {
            String sql = "SELECT * FROM let WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeFlight(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Korisnik getKorisnikByID(int id) {
        try {
            String sql = "SELECT * FROM korisnik WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeKorisnik(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Rezervacija getRezervacijaByID(int id) {
        try {
            String sql = "SELECT * FROM rezervacija WHERE id=" + id + ";";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return makeRezervacija(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** END OF GET BY ID **/
    /**********************************************************************************************************/



    /**********************************************************************************************************/
    /** START OF MAKE **/

    private AvionskaKarta makeAvionskaKarta(ResultSet rs) throws SQLException, ParseException {
        AvionskaKarta avionskaKarta = new AvionskaKarta();
        AvionskaKompanija avionskaKompanija = getAvionskaKompanijaByID(rs.getInt("avionska_kompanija_id"));
        Let flight = getFlightByID(rs.getInt("let_id"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        avionskaKarta
                .id(rs.getInt("id"))
                .one_way(rs.getBoolean("one_way"))
                .depart_date(sdf.parse(rs.getString("depart_date")))
                .flight(flight)
                .avionskaKompanija(avionskaKompanija)
                .available_count(rs.getInt("available_count"))
                .version(rs.getInt("version"));
        if (rs.getString("return_date") != null) {
            avionskaKarta.return_date(sdf.parse(rs.getString("return_date")));
        }
        return avionskaKarta;
    }

    private AvionskaKompanija makeAvionskaKompanija(ResultSet rs) throws SQLException {
        return new AvionskaKompanija()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .version(rs.getInt("version"));
    }

    public Korisnik makeKorisnik(ResultSet rs) throws SQLException{
        return new Korisnik()
            .id(rs.getInt("id"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .tipKorisnika(TipKorisnika.valueOf(rs.getString("tip_korisnika")))
            .version(rs.getInt("version"));
    }

    public Let makeFlight(ResultSet rs) throws SQLException {
        return new Let()
            .id(rs.getInt("id"))
            .grad_origin(getGradByID(rs.getInt("origin_grad_id")))
            .grad_destination(getGradByID(rs.getInt("destination_grad_id")))
            .version(rs.getInt("version"));
            //.avionskeKarte(out);
    }

    public Grad makeGrad(ResultSet rs) throws SQLException {
        return new Grad()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .version(rs.getInt("version"));
    }

    public Rezervacija makeRezervacija(ResultSet rs) throws SQLException {
        return new Rezervacija()
            .id(rs.getInt("id"))
            .available(rs.getInt("isAvailable") == 1)
            .flight(getFlightByID(rs.getInt("let_id")))
            .avionskaKarta(getAvionskaKartaByID(rs.getInt("avionska_karta_id")))
            .korisnik(getKorisnikByID(rs.getInt("korisnik_id")))
            .version(rs.getInt("version"));
    }

    /** END OF MAKE **/
    /**********************************************************************************************************/

    private boolean validateKarta(AvionskaKarta avionskaKarta) {
        if (avionskaKarta == null) return false;
        if (!validateFlight(avionskaKarta.getFlight())) return false;
        if (!validateCompany(avionskaKarta.getAvionskaKompanija())) return false;
        if (avionskaKarta.getDepart_date() == null) return false;
        if (!avionskaKarta.isOne_way()) {
            if (avionskaKarta.getReturn_date() == null) return false;
            if (avionskaKarta.getReturn_date().before(avionskaKarta.getDepart_date())) return false;
        }
        return true;
    }

    private boolean validateFlight(Let let) {
        if (let == null) return false;
        if (!validateCity(let.getGrad_destination())) return false;
        if (!validateCity(let.getGrad_origin())) return false;
        if (let.getGrad_destination().equals(let.getGrad_origin())) return false;
        return true;
    }

    private boolean validateCompany(AvionskaKompanija avionskaKompanija) {
        if (avionskaKompanija == null) return false;
        if (Util.isEmpty(avionskaKompanija.getName())) return false;
        return true;
    }

    private boolean validateCity(Grad grad) {
        if (grad == null) return false;
        if (Util.isEmpty(grad.getName())) return false;
        return true;
    }

    private boolean validateReservation(Rezervacija rezervacija) {
        if (rezervacija == null) return false;
        if (rezervacija.getAvionskaKarta() == null) return false;
        if (rezervacija.getFlight() == null) return false;
        if (!validateUser(rezervacija.getKorisnik())) return false;
        return true;
    }

    private boolean validateUser(Korisnik korisnik) {
        if (korisnik == null) return false;
        if (Util.isEmpty(korisnik.getUsername())) return false;
        if (Util.isEmpty(korisnik.getPassword())) return false;
        return true;
    }
}
