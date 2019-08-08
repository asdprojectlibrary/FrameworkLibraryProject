package dao.rdb.ModelDataAccess;

import config.LibraryManager;
import model.Author;
import model.Book;
import model.CheckoutEntry;
import model.Member;

public class ConcreteDataAccessFactory implements DataAccessFactory{

    private static DataAccessFactory instance;

    private ConcreteDataAccessFactory() {
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
                    instance = new ConcreteDataAccessFactory();
                }
            }
        }
        return instance;
    }
}
