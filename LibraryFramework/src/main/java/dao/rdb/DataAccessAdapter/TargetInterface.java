package dao.rdb.DataAccessAdapter;

import model.*;

import java.util.List;

public interface TargetInterface {

    public boolean save(Member libMemb);

    public boolean save(Book book);

    public boolean save(CheckoutEntry checkoutEntry);

    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord);

    // public boolean saveUser(User user);

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

}
