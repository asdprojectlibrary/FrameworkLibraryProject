package dao.rdb.ModelDataAccess;

import model.Author;
import model.Book;

import java.util.List;

public class AuthorDataAccess extends RDBDataAccess<Author> {

    public AuthorDataAccess() {
        super(Author.class);
    }
    @Override
    public List<Author> getAll() {
       return  null;
    }

    @Override
    public void save(Author author) {

    }

    @Override
    public Author get(String key) {
        return null;
    }
}