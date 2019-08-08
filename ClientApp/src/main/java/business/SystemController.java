package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.exceptions.AddBookException;
import business.exceptions.AddMemberException;
import business.exceptions.CheckoutException;
import business.exceptions.LoginException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import Service.*;
import model.Auth;
import model.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws Exception {

		/*DataAccess da = new DataAccessFacade();

		HashMap<String, User> map = da.readUserMap();

		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found.");
		}

		String passwordFound = map.get(id).getPassword();

		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect.");
		}

		 */

		UserService userService = new UserService();
		User user = userService.Login(id,password);

		currentAuth = user.getAuthorization();
		//
		// it's supposed to now show a new window

	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<LibraryMember> allMembers() {

		DataAccess da = new DataAccessFacade();
		List<LibraryMember> retval = new ArrayList<>();

		for (LibraryMember member : da.readMemberMap().values()) {
			retval.add(member);
		}

		return retval;
	}

	public void saveNewMember(LibraryMember member) throws AddMemberException {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	public LibraryMember getMember(String memberID) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> membersMap = da.readMemberMap();
		if (membersMap.containsKey(memberID)) {
			return membersMap.get(memberID);
		}
		return null;
	}

	@Override
	public void updateMember(LibraryMember member) {
		DataAccessFacade da = new DataAccessFacade();
		da.updateMember(member);
		
	}
	
	@Override
	public List<Book> allBooks() {
		DataAccess da = new DataAccessFacade();

		List<Book> retval = new ArrayList<>();

		for (Book book : da.readBooksMap().values()) {
			retval.add(book);
		}
		return retval;
	}

	@Override
	public List<Author> allAuthors() {
		DataAccess da = new DataAccessFacade();

		List<Author> retval = new ArrayList<>();

		for (Author author : da.readAuthorsMap().values()) {
			retval.add(author);
		}
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public void saveBook(Book book) throws AddBookException {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}
	
	public void updateBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.updateBook(book);
	}

	@Override
	public CheckoutRecord checkout(String id, String isbn) throws CheckoutException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> booksMap = da.readBooksMap();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		if (!memberMap.containsKey(id)) {
			throw new CheckoutException("Member ID " + id + " not found");
		}
		LibraryMember member = memberMap.get(id);
		if (!booksMap.containsKey(isbn)) {
			throw new CheckoutException("ISBN " + isbn + " not found");
		}
		Book book = booksMap.get(isbn);
		if (!book.isAvailable()) {
			throw new CheckoutException("Book with ISBN " + isbn + " is not available");
		}

		BookCopy bookCopy = book.getNextAvailableCopy();
		bookCopy.changeAvailability();
		CheckoutRecord checkoutRecord = member.getCheckoutRecord();

		CheckoutEntry checkoutEntry = new CheckoutEntry(bookCopy, checkoutRecord);
		checkoutRecord.addCheckoutEntry(checkoutEntry);
		member.setCheckoutRecord(checkoutRecord);
		bookCopy.setCheckoutEntry(checkoutEntry);
		
		book.updateCopies(bookCopy);

		da.updateMember(member);
		da.updateBook(book);
		return checkoutRecord;

	}

	@Override
	public LibraryMember getCheckoutRecord(String id) throws CheckoutException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		if (!memberMap.containsKey(id)) {
			throw new CheckoutException("ID " + id + " not found");
		}
	
        return  memberMap.get(id);
	}

	@Override
	public List<BookCopy> verifyOverdue(String isbn) throws CheckoutException {
		List<BookCopy> overDueCopies = new ArrayList<BookCopy>();
		List<Book> listOfBooks = allBooks();
		Book book = bookExistsWithISBN(isbn, listOfBooks);
		if (book != null) {
			BookCopy[] copies = book.getCopies();
			List<BookCopy> checkedOutCopies = new ArrayList<BookCopy>();
			
			for (BookCopy copy : copies) {
				if (!copy.isAvailable()) {
					checkedOutCopies.add(copy);
				}
			}
			if (checkedOutCopies.size() > 0) {
				for (BookCopy copy : checkedOutCopies) {
					// get checkout entry that has this copy and check due date
//					if due date is before today, add to overdueCheckOuts,
					CheckoutEntry checkoutEntry = copy.getCheckoutEntry();
					LocalDate dueDate = checkoutEntry.getDueDate();
					if(dueDate.isBefore(LocalDate.now())) {
						overDueCopies.add(copy);
					}									
				}
				if (overDueCopies.size() > 0) {
					return overDueCopies;

				} else {
					throw new CheckoutException("No checkout of this book is currently overdue.");
				}
			} else {
				throw new CheckoutException("No copy of this book is currently checked out.");

			}
		} else {
			throw new CheckoutException("Book Not Found!");
		}
		
	}

	
	private Book bookExistsWithISBN(String isbn, List<Book> listOfBooks) {
		for (Book book : listOfBooks) {
			if (book.getIsbn().equals(isbn)) {
				return book;
			}
		}
		return null;
	}
}
