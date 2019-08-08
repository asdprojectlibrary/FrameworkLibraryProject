package Service;

import exception.BookCopyNotAvailable;
import exception.BookNotFound;
import exception.LibraryMemberNotFound;
import model.Book;
import model.BookCopy;
import model.CheckoutEntry;
import model.Member;
import repository.BaseRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final public class CheckoutService {
    private BaseRepository<CheckoutEntry> repository;

    public CheckoutService() {
        repository = new BaseRepository<>(CheckoutEntry.class);
    }

    public void save(CheckoutEntry CheckoutEntry) {
        repository.getDataAccess().save(CheckoutEntry);
    }

    public List<CheckoutEntry> getAll() {
        return repository.getDataAccess().getAll();
    }

    public List<CheckoutEntry> getCheckoutEntries(String memberId, String isbn) {
        return repository.getDataAccess().getAll().stream()
                .filter(f ->
                        f.getCheckoutItem().getBook().getIsbn().equals(isbn)
                                &&
                                f.getMember().getId().equals(memberId)
                                &&
                                f.getDueDate().isBefore(ChronoZonedDateTime.from(LocalDate.now())))
                .collect(Collectors.toList());
    }

    public List<CheckoutEntry> getCheckoutEntries(String memberId) {
        return repository.getDataAccess().getAll().stream()
                .filter(f ->

                        f.getMember().getId().equals(memberId)
                                &&
                                f.getDueDate().isBefore(ChronoZonedDateTime.from(LocalDate.now())))
                .collect(Collectors.toList());
    }

    public CheckoutEntry getOne(String key) {
        return repository.getDataAccess().get(key);
    }

    public CheckoutEntry checkOut(String memberId, String isbn) throws Exception {

        MemberService memberService = new MemberService();
        Member member = memberService.getOne(memberId);

        BookService bookService = new BookService();
        Book book = bookService.getOne(isbn);

        if (member == null) {
            throw new LibraryMemberNotFound();
        }
        if (book == null) {
            throw new BookNotFound();
        } else {

            Optional<BookCopy> copy = book.getCopies().stream().filter(c -> c.isAvailable() == true).findFirst();

            if (copy != null) {

                BookCopy copy1 = copy.get();

                CheckoutEntry checkoutEntry = new CheckoutEntry();

                checkoutEntry.setId(System.currentTimeMillis() + "");
                checkoutEntry.setMember(member);
                checkoutEntry.setCheckoutItem(copy1);

                checkoutEntry.setCheckoutDate(ZonedDateTime.now());
                checkoutEntry.setDueDate(checkoutEntry.getCheckoutDate().plusDays(book.getMaxCheckoutLength()));

                save(checkoutEntry);

                for (BookCopy bookCopy : book.getCopies()) {

                    if (bookCopy.equals(copy1)) {
                        copy1.setAvailable(false);
                        break;
                    }
                }

                bookService.save(book);

                return checkoutEntry;

            } else {
                throw new BookCopyNotAvailable();
            }
        }

    }

    public List<CheckoutEntry> getOverdue(String isbn) {

        return repository.getDataAccess().getAll().stream()
                .filter(f -> f.getCheckoutItem().getBook().getIsbn().equals(isbn) && f.getDueDate().isBefore(ChronoZonedDateTime.from(LocalDate.now())))
                .collect(Collectors.toList());

    }
}
