package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;
import business.User;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS, CHECKOUT;
	}

	public static final String OUTPUT_DIR =  Paths.get(System.getProperty("user.dir"), 
	      "src", "resources", "dataaccess","storage").toString();

	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	//implement: other save operations
	public void saveNewMember(LibraryMember member) {
		updateMember(member);	
	}
	
	   //implement: other save operations
    public void updateMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);   
    }
	
	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readDataMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
	//static void loadMemberMap(List<LibraryMember> memberList) {
		
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}
 
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	
    static void loadCheckout(List<LibraryMember> memberList) {
        HashMap<String, CheckoutRecord> checkoutRecords = new HashMap<String, CheckoutRecord>();
        memberList.forEach(member -> checkoutRecords.put(member.getMemberId(), member.getCheckoutRecord()));
        saveToStorage(StorageType.CHECKOUT, checkoutRecords);
    }
	
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
		    
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			System.out.println("file path: " + path);
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	
	final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}



    @Override
    public void saveBook(Book book)
    {
       updateBook(book);
    }

    @Override
    public void updateBook(Book book)
    {
        HashMap<String, Book> books = readBooksMap();
        String bookId = book.getIsbn();
        books.put(bookId, book);
        saveToStorage(StorageType.BOOKS, books);  
    }

    @Override
    public void saveUser(User user)
    {
        updateUser(user);
    }

    @Override
    public void updateUser(User user)
    {
        HashMap<String, User> users = readUserMap();
        users.put(user.getId(), user);
        saveToStorage(StorageType.USERS, users); 
    }
    
    @SuppressWarnings("unchecked")
    public  HashMap<String,CheckoutRecord> readCheckoutRecordMap() {
        //Returns a Map with name/value pairs being
        //   isbn -> Book
        return (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CHECKOUT);
    }
    
    @Override
    public void saveCheckoutRecord(CheckoutRecord record)
    {
        updateCheckoutRecord(record);
    }

    @Override
    public void updateCheckoutRecord(CheckoutRecord record)
    {
        HashMap<String, CheckoutRecord> records = readCheckoutRecordMap();
        records.put(record.getMember().getMemberId(), record);
        saveToStorage(StorageType.CHECKOUT, records); 
    }
	
}
