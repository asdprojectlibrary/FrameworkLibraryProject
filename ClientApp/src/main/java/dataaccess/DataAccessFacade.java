//package dataaccess;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.List;
//
//import business.Author;
//import business.Book;
//import business.LibraryMember;
//import business.exceptions.AddBookException;
//import business.exceptions.AddMemberException;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//
//public class DataAccessFacade implements DataAccess {
//
//	enum StorageType {
//		BOOKS, MEMBERS, USERS, AUTHORS;
//	}
//
//	static String fileSeparator = "\\";
//
//	static {
//		String separator = File.separator;
//		if (separator.equalsIgnoreCase("/")) {
//			fileSeparator = separator;
//
//		}
//	}
//
//	//public static final String OUTPUT_DIR =  "\\src\\dataaccess\\storage";
//
//	//public static final String OUTPUT_DIR = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator
//	//		+ "dataaccess" + fileSeparator + "storage";
//	public static final String DATE_PATTERN = "MM/dd/yyyy";
//
//	private void saveMember(LibraryMember member) {
//		HashMap<String, LibraryMember> mems = readMemberMap();
//		String memberId = member.getMemberId();
//		mems.put(memberId, member);
//		saveToStorage(StorageType.MEMBERS, mems);
//	}
//
//	private void saveNewBook(Book book) {
//		HashMap<String, Book> books = readBooksMap();
//		String isbn = book.getIsbn();
//		books.put(isbn, book);
//		saveToStorage(StorageType.BOOKS, books);
//	}
//
//	// implement: other save operations
//	public void saveMember(LibraryMember member) throws AddMemberException {
//		HashMap<String, LibraryMember> mems = readMemberMap();
//		String memberId = member.getMemberId();
//		if (!mems.containsKey(memberId)) {
//			saveMember(member);
//		} else {
//			throw new AddMemberException("A Member has this ID already!");
//		}
//	}
//
//	public void saveNewBook(Book book) throws AddBookException {
//		HashMap<String, Book> books = readBooksMap();
//		String isbn = book.getIsbn();
//		if (!books.containsKey(isbn)) {
//			saveNewBook(book);
//		} else {
//			throw new AddBookException("A Book has this ISBN already!");
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public HashMap<String, Book> readBooksMap() {
//		// Returns a Map with name/value pairs being
//		// isbn -> Book
//		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
//	}
//
//	@SuppressWarnings("unchecked")
//	public HashMap<String, LibraryMember> readMemberMap() {
//		// Returns a Map with name/value pairs being
//		// memberId -> LibraryMember
//		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
//	}
//
//	@SuppressWarnings("unchecked")
//	public HashMap<Integer, Author> readAuthorsMap() {
//		// Returns a Map with id/value pairs being
//		// authorid -> Author
//		return (HashMap<Integer, Author>) readFromStorage(StorageType.AUTHORS);
//	}
//
//	@SuppressWarnings("unchecked")
//	public HashMap<String, User> readUserMap() {
//		// Returns a Map with name/value pairs being
//		// userId -> User
//		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
//	}
//
//	///// load methods - these place test data into the storage area
//	///// - used just once at startup
//	// static void loadMemberMap(List<LibraryMember> memberList) {
//
//	static void loadBookMap(List<Book> bookList) {
//		HashMap<String, Book> books = new HashMap<String, Book>();
//		bookList.forEach(book -> books.put(book.getIsbn(), book));
//		saveToStorage(StorageType.BOOKS, books);
//	}
//
//	static void loadUserMap(List<User> userList) {
//		HashMap<String, User> users = new HashMap<String, User>();
//		userList.forEach(user -> users.put(user.getId(), user));
//		saveToStorage(StorageType.USERS, users);
//	}
//
//	static void loadAuthorMap(List<Author> authorList) {
//		HashMap<Integer, Author> authors = new HashMap<Integer, Author>();
//		authorList.forEach(author -> authors.put(author.getID(), author));
//		saveToStorage(StorageType.AUTHORS, authors);
//	}
//
//	static void loadMemberMap(List<LibraryMember> memberList) {
//		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
//		memberList.forEach(member -> members.put(member.getMemberId(), member));
//		saveToStorage(StorageType.MEMBERS, members);
//	}
//
//	static void saveToStorage(StorageType type, Object ob) {
//
//
//
//
//		ObjectOutputStream out = null;
//		try {
//			Path path = FileSystems.getDefault().getPath(type.toString());
//
//			out = new ObjectOutputStream(Files.newOutputStream(path));
//			out.writeObject(ob);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setHeaderText(e.getMessage());
//			alert.setContentText(e.getStackTrace().toString());
//			alert.showAndWait();
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//	}
//
//	static Object readFromStorage(StorageType type) {
//		ObjectInputStream in = null;
//		Object retVal = null;
//		try {
//			Path path = FileSystems.getDefault().getPath(type.toString());
//			in = new ObjectInputStream(Files.newInputStream(path));
//			retVal = in.readObject();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//		return retVal;
//	}
//
//	final static class Pair<S, T> implements Serializable {
//
//		S first;
//		T second;
//
//		Pair(S s, T t) {
//			first = s;
//			second = t;
//		}
//
//		@Override
//		public boolean equals(Object ob) {
//			if (ob == null)
//				return false;
//			if (this == ob)
//				return true;
//			if (ob.getClass() != getClass())
//				return false;
//			@SuppressWarnings("unchecked")
//			Pair<S, T> p = (Pair<S, T>) ob;
//			return p.first.equals(first) && p.second.equals(second);
//		}
//
//		@Override
//		public int hashCode() {
//			return first.hashCode() + 5 * second.hashCode();
//		}
//
//		@Override
//		public String toString() {
//			return "(" + first.toString() + ", " + second.toString() + ")";
//		}
//
//		private static final long serialVersionUID = 5399827794066637059L;
//	}
//	///
//	/// NEW METHODS ADDED BELOW
//
//	@Override
//	public void updateMember(LibraryMember member) {
//		// this method is called either after a new checkout entry
//		// has been created and added to a checkout record for a member.
//		// OR we edit a member's personal details
//		// So the member's record is saved.
//		saveMember(member);
//	}
//
//	@Override
//	public void updateBook(Book book) {
//		saveNewBook(book);
//
//	}
//
//}
