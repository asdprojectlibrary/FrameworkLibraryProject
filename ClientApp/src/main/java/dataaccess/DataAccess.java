package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.exceptions.AddBookException;
import business.exceptions.AddMemberException;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<Integer,Author> readAuthorsMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member) throws AddMemberException; 
	public void updateMember(LibraryMember member);
	public void saveNewBook(Book book) throws AddBookException;
	public void updateBook(Book book);
}
