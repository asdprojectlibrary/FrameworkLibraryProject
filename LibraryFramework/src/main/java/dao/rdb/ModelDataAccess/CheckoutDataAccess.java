package dao.rdb.ModelDataAccess;

import model.Author;
import model.Book;
import model.CheckoutEntry;

import java.util.List;

public class CheckoutDataAccess extends RDBDataAccess<CheckoutEntry> {

    public CheckoutDataAccess() {
        super(CheckoutEntry.class);
    }
    @Override
    public List<CheckoutEntry> getAll() {
        return null;
    }

    @Override
    public void save(CheckoutEntry checkoutEntry) {

    }

    @Override
    public CheckoutEntry get(String key) {
        return null;
    }
}
