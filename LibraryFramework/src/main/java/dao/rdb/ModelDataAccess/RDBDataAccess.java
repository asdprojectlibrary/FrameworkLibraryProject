package dao.rdb.ModelDataAccess;

import dao.IDataAccess;
import dao.rdb.dataaccessadapter.Adapter;
import dao.rdb.dataaccessadapter.TargetInterface;
import model.BaseModel;

import java.util.List;

public abstract class RDBDataAccess<T extends BaseModel> implements IDataAccess<T> {

    private Class<T> type;
    protected TargetInterface rdb;

    public  RDBDataAccess(Class<T> type) {
        rdb = new Adapter();
        this.type = type;
    }

    @Override
    public void save(List<T> items) {
        for(T t:items){
            save(t);
        }
    }

}
