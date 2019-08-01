package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;
import business.User;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap(); 
    public HashMap<String, CheckoutRecord> readCheckoutRecordMap(); 
	public void saveNewMember(LibraryMember member); 
    public void updateMember(LibraryMember member); 
    public void saveBook(Book book); 
    public void updateBook(Book book); 
    public void saveUser(User user); 
    public void updateUser(User user); 
    public void saveCheckoutRecord(CheckoutRecord record); 
    public void updateCheckoutRecord(CheckoutRecord record); 
}
