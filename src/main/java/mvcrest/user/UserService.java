package mvcrest.user;

import java.util.List;

/**
 * Servisni sloj se bavi svom "biznis logikom"
 */
public class UserService {

    public List<User> getUsers(){
        return UserRepository.getUsers();
    }

    public User getUserById(Integer id){
        return UserRepository.getUserById(id);
    }

    public User addUser(User user){
        return UserRepository.addUser(user);
    }

    public User findUser(String username, String password){
        return UserRepository.findUser(username, password);
    }

}
