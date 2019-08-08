package config;

import model.Member;

import java.sql.Savepoint;
import java.util.List;

public abstract class DataLoader {
    protected  final void Savepoint(){
        loadmembers();
        loadmembers1();
        loadmembers2();
    }

    abstract void loadmembers();
    abstract void loadmembers1();
    abstract void loadmembers2();
}
