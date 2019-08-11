package dao.rdb.ModelDataAccess;

import config.LibraryManager;
import model.*;

public class RDBDataAccessFactory implements DataAccessFactory{

    private static DataAccessFactory instance;

    private RDBDataAccessFactory() {
    }

    public <T> RDBDataAccess getDataAccess(Class<T> type) {

        if (type == Book.class) {
            return new BookDataAccess();
        }
        else  if (type == Member.class) {
            return new MemberDataAccess();
        }
        else  if (type == Author.class) {
            return new AuthorDataAccess();
        }
        else  if (type == CheckoutEntry.class) {
            return new CheckoutDataAccess();
        }else  if (type == User.class) {
            return new UserDataAccess();
        }

        return null;
    }

    public static DataAccessFactory getInstance() {

        //Singleton
        if (instance == null) {
            synchronized (LibraryManager.class) {
                if (instance == null) {
                    instance = new RDBDataAccessFactory();
                }
            }
        }
        return instance;
    }
}
