package business;

import business.exceptions.AddBookException;
import business.exceptions.AddMemberException;
import business.exceptions.CheckoutException;
import model.*;
import model.Member;

import java.util.List;

public interface ControllerInterface {
    public void login(String id, String password) throws Exception;

    public List<String> allMemberIds();

    public List<String> allBookIds();

    List<Member> allMembers();

    public void saveMember(Member member) throws Exception;

    public Member getMember(String memberId);

    public void updateMember(Member member) throws Exception;

    List<Book> allBooks();

    List<Author> allAuthors();

    public void saveNewBook(Book book, int numOfCopies) throws AddBookException;

    public void updateBook(Book book) throws AddBookException;

    public CheckoutEntry checkout(String id, String isbn) throws Exception;

    //public Member getCheckoutEntries(String id) throws CheckoutException;

    public List<CheckoutEntry> verifyOverdue(String isbn) throws CheckoutException;
    public void addCopies(String isbn, int numOfCopies);

    List<CheckoutEntry> getCheckoutEntries(String memberId, String isbn);
    public List<CheckoutEntry> getCheckoutEntries(String memberId );
}
