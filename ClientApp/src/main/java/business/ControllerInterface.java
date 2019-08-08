package business;

import java.util.List;

import business.Book;
import business.exceptions.AddBookException;
import business.exceptions.AddMemberException;
import business.exceptions.CheckoutException;
import business.exceptions.LoginException;

public interface ControllerInterface {
	public void login(String id, String password) throws Exception;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	List<LibraryMember> allMembers();
	public void saveNewMember(LibraryMember member) throws AddMemberException;
	public LibraryMember getMember(String memberId);
	public void updateMember(LibraryMember member);
	List<Book> allBooks();
	List<Author> allAuthors();
	public void saveBook(Book book) throws AddBookException;
	public void updateBook(Book book);
	public CheckoutRecord checkout(String id, String isbn)  throws CheckoutException;
	public LibraryMember getCheckoutRecord(String id) throws CheckoutException;
	public List<BookCopy> verifyOverdue(String isbn) throws CheckoutException;
	
}
