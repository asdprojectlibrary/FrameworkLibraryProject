package dao.rdb.dataaccessadapter;

import model.*;

import java.util.HashMap;
import java.util.List;

public interface DBTarget {

    public boolean save(Member libMemb);

    public boolean save(Book book);

    public boolean save(CheckoutEntry checkoutEntry);

    public boolean save(Author author);

    public boolean save(User user);

    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord);

    //==========If user equal null it does not exist=========
    public User searchUserWithPWD(String userId,String password);
    public User searchUser(String userId);
    public List<User> searchAllUsers();



    public boolean updateLibraryMember(Member libraryMember);

    public boolean updateBook(Book book);

    public boolean updateCheckoutRecord(CheckoutRecord chkOutRecord);

    public Member searchLibraryMemberById(String memberId);

    public List<Member> searchAllLibraryMember();

    public Book searchBookByISBN(String ISBN);

    public CheckoutEntry searchCheckoutEntryById(String id);

    public List<Book> searchAllBook();

    public List<BookCopy> searchBookCopies(String isbn);


    public List<CheckoutRecord> searchAllCheckoutRecord();
    public List<CheckoutEntry> searchAllCheckoutEntry();
    public List<Author> searchAllAuthors();

    //========if it already exist it will return false and will not reset it======
    public boolean createTables();

}
