package Service;

import exception.InvalidCredentials;
import exception.LoginException;
import model.User;
import repository.BaseRepository;

import java.util.List;

final public class UserService {

    private BaseRepository<User> repository;

    public UserService() {
        repository = new BaseRepository<>(User.class);
    }

    public void save(User user) {
        repository.getDataAccess().save(user);
    }

    public void save(List<User> books) {
        repository.getDataAccess().save(books);
    }

    public List<User> getAll() {
        return repository.getDataAccess().getAll();
    }

    public User getOne(String id) {
        return repository.getDataAccess().get(id);
    }

    public  User Login(String id, String password) throws Exception {

        if (id.isEmpty() || password.isEmpty()) {
            throw new InvalidCredentials("Please fill all fields correctly!!!");
        }
        else
        {

            User user =getOne(id);

            if(user==null){
                throw new  Exception("User ("+id+") not found");
            }
            String passwordFound = user.getPassword();

            if (!passwordFound.equals(password)) {
                throw new LoginException("Password incorrect.");
            }

            return  user;
        }
    }


}


