package mvcrest.user;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import mvcrest.authentication.AuthService;

/**
 * Controller ili Endpoint ce se baviti povezivanjem HTTP METODE/URL do biznis logike
 */
@Path("/users")
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password ){
        User user = userService.findUser(username, password);
        if(user != null) {
            //user.setJWTToken(AuthService.generateJWT(user));
            return Response.ok(user).build();
        }
        return Response.status(404).build();
    }

    /**
     * Dohvatanje svih User-a
     * Putanja je rest/users
     * <p>
     * Takodje je moguce isfiltrirati korisnike po imenu.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON) //Bitno je da navedemo sta je rezultujuci content type ove metode
    public List<User> getUsers(@HeaderParam("Authorization") String auth) {
        if(AuthService.isAuthorized(auth)) {
            return userService.getUsers();
        }
        return null;
    }


    /**
     * Dohvatanje jednog resursa po ID-u.
     * Putanja bi bila npr. rest/users/4
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}") // {id} navodi da se radi o promenljivoj id koja ce se proslediti kao argument metode
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") int id) { // Da bi se u tacan argument prosledio id mora da se oznaci anotacijom
        return userService.getUserById(id);
    }

    /**
     * Cuvanje jednog resursa. Rezultat je taj resurs sa ID-em.
     * Putanja je rest/users
     *
     * @param user
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    // Prilikom POST-a nam je bitno sta je content type onoga sto ova metoda prima zbog deserijalizacije
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
        return userService.addUser(user);
    }
}
