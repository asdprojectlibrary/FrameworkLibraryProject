package dao.rdb.ModelDataAccess;

import config.LibraryManager;
import model.Author;
import model.Book;
import model.CheckoutEntry;
import model.Member;

final public class DataAccessFactory {

    private static DataAccessFactory instance;

    private DataAccessFactory() {
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
        }

        return null;
    }

    public static DataAccessFactory getInstance() {

        //Singleton
        if (instance == null) {
            synchronized (LibraryManager.class) {
                if (instance == null) {
                    instance = new DataAccessFactory();
                }
            }
        }
        return instance;
    }
}
