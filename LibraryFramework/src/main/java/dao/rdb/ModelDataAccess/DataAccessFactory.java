package dao.rdb.ModelDataAccess;

public interface DataAccessFactory {

    public <T> RDBDataAccess getDataAccess(Class<T> type);


}
