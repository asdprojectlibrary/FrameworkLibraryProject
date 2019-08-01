package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import business.Address;
import business.LibraryMember;
import dataaccess.dao.LibraryMemberRepository;
import javafx.fxml.Initializable;


public class MemberController implements Initializable {
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean saveNewMember(String fName,String lName,String phone,String street,String city,String state,String zip){
		boolean returnedValue=true;
		String memberId=getMemberId();
		
		//Creating Address and Member objects
		Address adr=new Address(street,city,state,zip);
		LibraryMember libMember=new LibraryMember(memberId,fName,lName,phone,adr);		
		
		LibraryMemberRepository.addMember(libMember);		
		return returnedValue;
	}

	public String getMemberId() {
	    return UUID.randomUUID().toString();
	}
	
	
	
}
