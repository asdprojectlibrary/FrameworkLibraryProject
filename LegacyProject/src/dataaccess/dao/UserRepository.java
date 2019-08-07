package dataaccess.dao;

import java.util.Map;

import business.User;
import dataaccess.exception.InvalidCredentials;
import dataaccess.exception.UserNotFound;

public class UserRepository extends ObjectRepository
{
    public static User getuser(String userId, String password) throws UserNotFound, InvalidCredentials {
        Map<String, User> userMembersMap = dbAccess.readUserMap();
        User user = userMembersMap.get(userId);
        if(user == null)
            throw new UserNotFound();
        if(user.getPassword().equals(password) == false)
            throw new InvalidCredentials();
        return user;
    }
    
    public static void updateUser(User user) {
        dbAccess.updateUser(user);    
    }
}
