package dao.rdb.ModelDataAccess;

import dao.IDataAccess;
import dao.rdb.dataaccessadapter.DBAdapter;
import dao.rdb.dataaccessadapter.DBTarget;
import model.BaseModel;
import model.Member;
import model.User;

import java.util.List;

public class UserDataAccess extends RDBDataAccess<User> {

    public UserDataAccess() {
        super(User.class);
    }

    @Override
    public List<User> getAll() {
        return rdb.searchAllUsers();
    }

    @Override
    public void save(User usr) {
        rdb.save(usr);
    }

    @Override
    public User get(String key) {
        return rdb.searchUser(key);
    }
}
