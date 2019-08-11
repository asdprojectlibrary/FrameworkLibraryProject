package dao.flatfile;

import dao.IDataAccess;
import model.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FlatFileDataAccess<T extends BaseModel> implements IDataAccess<T> {

    private FlatFileStorage storage;
    private String storageType;
    private Class<T> type;

    public FlatFileDataAccess(Class<T> type) {
        storage = new FlatFileStorage();
        this.storageType = type.getName();
        this.type =type;
    }

    @Override
    public List<T> getAll() {
        return readAll().values().stream().collect(Collectors.toList());
    }

    @Override
    public void save(List<T> list) {
        HashMap<String, T> items = readAll();

        for (T t : list) {
            items.put(t.getId(), t);
        }
        storage.saveToStorage(storageType, items);
    }

    private HashMap<String, T> readAll() {

        HashMap<String, T> retVal = null;
        try {
            retVal = (HashMap<String, T>) storage.readFromStorage(storageType);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (retVal == null)
            retVal = new HashMap<>();

        return retVal;
    }

    @Override
    public void save(T t) {

        int i = 1;
        long time = System.currentTimeMillis();

        HashMap<String, T> items = readAll();
        if (t.getId() == null || t.getId().equals(""))
            t.setId(time + i++ + "");

        items.put(t.getId(), t);
        storage.saveToStorage(storageType, items);
    }

    @Override
    public T get(String key) {
        HashMap<String, T> items = readAll();
        return items.get(key);
    }


}
