package practica1.aplicacion.com.mynoteapp;

import java.util.ArrayList;
import java.util.List;


public class UserBL {

    public static List<User> users = new ArrayList<>();

    static{
        users.add(new User(100,"CHACHO","admin","juan camarena"));
    }

    public static User login(String username, String password){
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static User getUser(String username){
        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

}
