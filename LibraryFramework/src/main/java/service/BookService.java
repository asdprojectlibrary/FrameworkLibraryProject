package service;

import repository.BaseRepository;
import model.Book;
import model.BookCopy;

import java.util.List;

final public class BookService {

    private BaseRepository<Book> repository;

    public BookService() {
        repository = new BaseRepository<>(Book.class);
    }

    public void save(Book book) {
        repository.getDataAccess().save(book);
    }

    public void save(List<Book> books) {
        repository.getDataAccess().save(books);
    }

    public List<Book> getAll() {
        return repository.getDataAccess().getAll();
    }

    public Book getOne(String isbn) {
        return repository.getDataAccess().get(isbn);
    }

    public void addCopies(String isbn, int num) {

        Book book = getOne(isbn);
        int lastNumber=book.getNumCopies();

        for (int i = 1; i <=num; i++) {

            BookCopy copy = new BookCopy();
            copy.setBook(book);
            copy.setAvailable(true);
            copy.setCopyNum(lastNumber+i);
            book.getCopies().add(copy);

        }

        save(book);

    }

}


