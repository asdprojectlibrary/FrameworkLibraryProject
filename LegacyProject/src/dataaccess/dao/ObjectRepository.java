package dataaccess.dao;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public abstract class ObjectRepository
{
    final static DataAccess dbAccess = new DataAccessFacade();
}
