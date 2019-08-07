package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import business.User;
import business.value.Permission;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.dao.UserRepository;
import dataaccess.exception.InvalidCredentials;
import dataaccess.exception.UserNotFound;

public class SystemController implements ControllerInterface {
	public static List<Permission> currentAuth = null;
	public static User currentUser = null;
	
	public void login(String userId, String password) throws UserNotFound, InvalidCredentials, IOException{
	    currentUser = UserRepository.getuser(userId, password);
        currentAuth = currentUser.getAuthorization();
        MainController mainController = new MainController();
        mainController.loadView();
	}
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public boolean saveLibraryMember(String fName, String lName, String phone, String street, String city, String state,
			String zip) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
