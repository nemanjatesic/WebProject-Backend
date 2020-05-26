package mvcrest.avioni;

import mvcrest.authentication.AuthService;
import mvcrest.user.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/avioni")
public class AvioniController {

    private final AvioniService avioniService;

    public AvioniController() {
        this.avioniService = new AvioniService();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Korisnik korisnik) {
        Korisnik korisnikTmp = avioniService.getKorisnikByUsernameAndPassword(korisnik.getUsername(), korisnik.getPassword());
        if(korisnikTmp != null) {
            korisnikTmp.setJWTToken(AuthService.generateJWT(korisnik));
            return Response.ok(korisnikTmp).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path("/createKorisnik")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKorisnik(Korisnik korisnik) {
        return Response.ok(avioniService.createKorisnik(korisnik)).build();
    }

    @POST
    @Path("/createCompany")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompany(AvionskaKompanija avionskaKompanija) {
        boolean kompanija = avioniService.createCompany(avionskaKompanija);
        if (kompanija)
            return Response.ok().build();
        else
            return Response.status(409).build();
    }

    @POST
    @Path("/changeCompanyName")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeCompanyName(AvionskaKompanija avionskaKompanija, @QueryParam("name") String newName) {
        boolean kompanija = avioniService.changeCompanyName(avionskaKompanija, newName);
        if (kompanija)
            return Response.ok().build();
        else
            return Response.status(409).build();
    }

    @GET
    @Path("/letovi")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLetovi(@HeaderParam("Authorization") String auth) {
        //if (AuthService.isAuthorized(auth))
            return Response.ok(avioniService.getLetovi()).build();
        //else
        //    return Response.status(403).build();
    }

    @GET
    @Path("/rezervacijeByUsername")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRezervacijeForUsername(@HeaderParam("Authorization") String auth, @QueryParam("username") String username) {
        if (AuthService.isAuthorized(auth))
            return Response.ok(avioniService.getRezervacijeForUsername(username)).build();
        else
            return Response.status(403).build();
    }

    @GET
    @Path("/kartaID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKartaByID(@QueryParam("kartaID") int ID) {
        AvionskaKarta avionskaKarta = avioniService.getKartaByID(ID);
        if (avionskaKarta == null)
            return Response.status(404).build();
        return Response.ok(avionskaKarta).build();
    }

    @PATCH
    @Path("/modifyKarta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyKarta(AvionskaKarta avionskaKarta) {
        if (avioniService.modifyKarta(avionskaKarta)) {
            return Response.ok().build();
        }else {
            return Response.status(409).build();
        }
    }

    @GET
    @Path("/kompanije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKompanije() {
        return Response.ok(avioniService.getKompanije()).build();
    }

    @GET
    @Path("/kompanijaById")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKompanijaById(@QueryParam("kompanijaID") int ID) {
        var kompanija = avioniService.getKompanijaById(ID);
        if (kompanija != null)
            return Response.ok(kompanija).build();
        else
            return Response.status(404).build();
    }

    @GET
    @Path("/getCardsForCompanyId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardsForCompanyId(@QueryParam("kompanijaID") int ID) {
        return Response.ok(avioniService.getCardsForCompanyId(ID)).build();
    }

    @DELETE
    @Path("/deleteKarta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKartaByID(@QueryParam("kartaID") int ID) {
        return Response.ok(avioniService.deleteKartaByID(ID)).build();
    }

    @DELETE
    @Path("/deleteCompany")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompanyByID(@QueryParam("kompanijaID") int ID) {
        return Response.ok(avioniService.deleteCompanyByID(ID)).build();
    }

    @DELETE
    @Path("/deleteRezervacija")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRezervacijaByID(@QueryParam("rezervacijaID") int ID, @HeaderParam("Authorization") String auth) {
        if (AuthService.isAuthorized(auth))
            return Response.ok(avioniService.deleteRezervacijaByID(ID)).build();
        else
            return Response.status(403).build();
    }

    @GET
    @Path("/karte")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKarte() {
        return Response.ok(avioniService.getKarte()).build();
    }

    @POST
    @Path("/filterKarte")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByOneWay(Filter filter) {
        return Response.ok(avioniService.filter(filter)).build();
    }

    @POST
    @Path("/addKarta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AvionskaKarta addKarta(AvionskaKarta avionskaKarta) {
        return avioniService.addKarta(avionskaKarta);
    }

    @POST
    @Path("/addRezervacija")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRezervacija(Rezervacija rezervacija) {
        int code = avioniService.addRezervacija(rezervacija);
        if (code == 200) {
            return Response.ok(code).build();
        }
        return Response.status(code).build();
    }

    @POST
    @Path("/filterRezervacijeForUsername")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterRezervacijeForUsername(Filter filter, @HeaderParam("Authorization") String auth, @QueryParam("username") String username) {
//        int code = avioniService.addRezervacija(rezervacija);
//        if (code == 200) {
//            return Response.ok(code).build();
//        }
//        return Response.status(code).build();

        if (AuthService.isAuthorized(auth))
            return Response.ok(avioniService.filterRezervacijeForUsername(username, filter)).build();
        else
            return Response.status(403).build();
    }
}
