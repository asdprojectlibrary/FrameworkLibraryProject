package business;

import business.exceptions.CheckoutException;
import model.*;
import service.AuthorService;
import service.BookService;
import service.MemberService;
import service.UserService;
import service.CheckoutService;
import config.DataLoader;

import java.util.List;
import java.util.stream.Collectors;

public class SystemController implements ControllerInterface {

    public static Auth currentAuth = null;

    public void login(String id, String password) throws Exception {

        UserService userService = new UserService();
        User user = userService.Login(id, password);

        currentAuth = user.getAuthorization();
    }

    @Override
    public List<String> allMemberIds() {
        return allMembers().stream().map(c -> c.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Member> allMembers() {

        return new MemberService().getAll();

    }

    public void saveMember(Member member) throws Exception {
        new MemberService().save(member);
    }

    public Member getMember(String memberID) {
        return new MemberService().getOne(memberID);
    }

    @Override
    public void updateMember(Member member) throws Exception {
        saveMember(member);
    }

    @Override
    public List<Book> allBooks() {
        return new BookService().getAll();
    }

    @Override
    public List<Author> allAuthors() {
        return new AuthorService().getAll();
    }

    @Override
    public List<String> allBookIds() {
        return allBooks().stream().map(c -> c.getId()).collect(Collectors.toList());
    }

    public void saveNewBook(Book book, int numOfCopies) {

        BookService bookService = new BookService();
        bookService.save(book);
        bookService.addCopies(book.getIsbn(), numOfCopies);

    }

    public void addCopies(String isbn, int numOfCopies) {
        new BookService().addCopies(isbn, numOfCopies);
    }

    @Override
    public List<CheckoutEntry> getCheckoutEntries(String memberId, String isbn) {
        return new CheckoutService().getCheckoutEntries(memberId, isbn);
    }

    @Override
    public List<CheckoutEntry> getCheckoutEntries(String memberId) {
        return new CheckoutService().getCheckoutEntries(memberId);
    }

    public void updateBook(Book book) {
        new BookService().save(book);
    }

    @Override
    public CheckoutEntry checkout(String id, String isbn) throws Exception {

        return new CheckoutService().checkOut(id, isbn);

    }


    @Override
    public List<CheckoutEntry> verifyOverdue(String isbn) throws CheckoutException {
        return new CheckoutService().getOverdue(isbn);
//        List<BookCopy> overDueCopies = new ArrayList<BookCopy>();
//        List<Book> listOfBooks = allBooks();
//        Book book = bookExistsWithISBN(isbn, listOfBooks);
//        if (book != null) {
//            BookCopy[] copies = book.getCopies();
//            List<BookCopy> checkedOutCopies = new ArrayList<BookCopy>();
//
//            for (BookCopy copy : copies) {
//                if (!copy.isAvailable()) {
//                    checkedOutCopies.add(copy);
//                }
//            }
//            if (checkedOutCopies.size() > 0) {
//                for (BookCopy copy : checkedOutCopies) {
//                    // get checkout entry that has this copy and check due date
////					if due date is before today, add to overdueCheckOuts,
//                    CheckoutEntry checkoutEntry = copy.getCheckoutEntries();
//                    LocalDate dueDate = checkoutEntry.getDueDate();
//                    if (dueDate.isBefore(LocalDate.now())) {
//                        overDueCopies.add(copy);
//                    }
//                }
//                if (overDueCopies.size() > 0) {
//                    return overDueCopies;
//
//                } else {
//                    throw new CheckoutException("No checkout of this book is currently overdue.");
//                }
//            } else {
//                throw new CheckoutException("No copy of this book is currently checked out.");
//
//            }
//        } else {
//            throw new CheckoutException("Book Not Found!");
//        }
        //     return null;

    }


    private Book bookExistsWithISBN(String isbn, List<Book> listOfBooks) {
//        for (Book book : listOfBooks) {
//            if (book.getIsbn().equals(isbn)) {
//                return book;
//            }
//        }
        return null;
    }
}
