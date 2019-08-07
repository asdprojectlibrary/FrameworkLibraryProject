package dao.rdb.ModelDataAccess;

import model.Book;
import model.Member;

import java.util.List;

public class BookDataAccess extends RDBDataAccess<Book> {

    public BookDataAccess() {
        super(Book.class);
    }

    @Override
    public List<Book> getAll() {
        return rdb.searchAllBook();
    }

    @Override
    public void save(Book book) {
        rdb.save(book);
    }

    @Override
    public Book get(String key) {
        return rdb.searchBookByISBN(key);
    }
}

