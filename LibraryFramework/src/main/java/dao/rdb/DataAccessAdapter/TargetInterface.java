package dao.rdb.DataAccessAdapter;

import model.Book;
import model.BookCopy;
import model.CheckoutRecord;
import model.Member;

import java.util.List;

public interface TargetInterface {

    public boolean save(Member libMemb);

    public boolean save(Book book);

    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord);

   // public boolean saveUser(User user);

    public boolean updateLibraryMember(Member libraryMember);

    public boolean updateBook(Book book);

    public boolean updateCheckoutRecord(CheckoutRecord chkOutRecord);

    //public boolean updateUser(User user);

    public Member searchLibraryMemberById(Integer memberId);

    public List<Member> searchAllLibraryMember();

    public Book searchBookByISBN(String ISBN);

    public List<Book> searchAllBook();

    public List<BookCopy> searchBookCopies(String isbn);


    public List<CheckoutRecord> searchAllCheckoutRecord(Integer memberId);

}
