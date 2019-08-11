package dao.rdb.ModelDataAccess;

import config.LibraryManager;
import model.Author;
import model.Book;
import model.CheckoutEntry;
import model.Member;

 public interface DataAccessFactory {

    public <T> RDBDataAccess getDataAccess(Class<T> type);


}
