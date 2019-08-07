package dao.rdb;

import dao.IDataAccess;
import dao.rdb.DataAccessAdapter.Adapter;
import dao.rdb.DataAccessAdapter.TargetInterface;
import model.Author;
import model.BaseModel;
import model.Book;

import java.util.List;

public class RDBDataAccess<T extends BaseModel> implements IDataAccess<T> {

    private Class<T> type;
    private TargetInterface rdb;

    public  RDBDataAccess(Class<T> type) {
        rdb = new Adapter();
        this.type = type;
    }

    @Override
    public List<T> getAll() {

        if(type == Book.class){
           return (List<T>) rdb.searchAllBook();
        }
        else if(type == Author.class){
            return null;// (List<T>) ();
        }
        return null;
    }

    @Override
    public void save(List<T> t) {
        //rdb.save(t);
    }

    @Override
    public void save(T t) {

    }

    @Override
    public T get(String key) {
        return null;
    }

}
