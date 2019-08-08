package repository;

import config.FlatFileConfig;
import config.IConfig;
import config.LibraryManager;
import dao.IDataAccess;
import dao.flatfile.FlatFileDataAccess;
import dao.rdb.ModelDataAccess.ConcreteDataAccessFactory;
import dao.rdb.ModelDataAccess.DataAccessFactory;
import model.BaseModel;

final public class BaseRepository<T extends BaseModel> {

    IDataAccess<T> dataAccess;


    public BaseRepository(Class<T> type)  {

        IConfig config = LibraryManager.getInstance().getConfig();

        if (config != null) {
            if (config instanceof FlatFileConfig) {
                this.dataAccess = new FlatFileDataAccess<>(type);
            } else {
                //Factory method
                DataAccessFactory dataAccessFactory= ConcreteDataAccessFactory.getInstance();
                this.dataAccess = dataAccessFactory.getDataAccess(type);
            }
        }


    }

    public IDataAccess<T> getDataAccess() {
        return dataAccess;
    }

}
