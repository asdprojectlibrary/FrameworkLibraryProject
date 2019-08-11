package dao;

import model.BaseModel;

import java.util.List;

public interface IDataAccess<T extends BaseModel> {

    List<T> getAll();

    void save(List<T> t);

    void save(T t);

    T get(String key);

}
