package mvcrest.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Repository se ovde bavi imitacijom komunikacije sa bazom
 */
public class UserRepository {

    private static String[] FIRST_NAME_LIST = {"John-James", "Justine", "Ahsan", "Leja", "Jad", "Vernon", "Cara", "Eddison", "Eira", "Emily"};
    private static String[] LAST_NAME_LIST = {"Booker", "Summers", "Reyes", "Rahman", "Crane", "Cairns", "Hebert", "Bradshaw", "Shannon", "Phillips"};
    private static List<User> USER_LIST;

    /**
     * Java identifikuje objekte po njihovom hashCode-u.
     * Izmenom vrednosti atributa objekta, i hashCode objekta se menja.
     * Zato je najbolje kao monitor koristiti final Object().
     */
    private static final Object LOCK = new Object();

    static {
        USER_LIST = generateUsers();
    }

    /**
     * Generise 10 korisnika birajuci nasumicno imena i prezimena iz liste
     *
     * @return
     */
    private static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        Random random = new Random();

        User user = new User();
        user.setId();
        user.setUsername("a");
        user.setPassword("a");

        users.add(user);
        for (int i = 0; i < 10; i++) {

            user = new User();
            user.setId();
            user.setUsername(FIRST_NAME_LIST[random.nextInt(FIRST_NAME_LIST.length)]);
            user.setPassword(LAST_NAME_LIST[random.nextInt(LAST_NAME_LIST.length)]);

            users.add(user);
        }

        return users;
    }

    public static List<User> getUsers() {
        return USER_LIST;
    }

    public static User getUserById(Integer id) {
        synchronized (LOCK) {
            return USER_LIST.get(id);
        }
    }

    public static User addUser(User user) {

        /* Referenca 'user' se nalazi na steku trenutnog treda,
           zato sa njom mozemo raditi bez kljuca.
         */
        user.setId();

        /*
            USER_LIST je zajednicki resurs, zato mu pristupamo pod kljucem.
         */
        synchronized (LOCK) {
            USER_LIST.add(user);
        } // Kada smo zavrsili sa zajednickim resursom, oslobadjamo kljuc

        return user;
    }

    public static User findUser(String username, String password) {
        synchronized (LOCK) {
            for (User u : USER_LIST) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    return u;
                }
            }
        }
        return null;
    }

    public static User findUserByUsername(String username) {
        synchronized (LOCK) {
            for (User u : USER_LIST) {
                if (u.getUsername().equals(username)) {
                    return u;
                }
            }
        }
        return null;
    }
}
