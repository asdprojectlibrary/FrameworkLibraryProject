package dataaccess;

import Service.AuthorService;
import Service.BookService;
import Service.MemberService;
import Service.UserService;
import config.DataLoader;

public class DataLoaderImpl extends DataLoader {

    @Override
    public void loadBooks() {
        new BookService().save(TestData.allBooks);
    }

    @Override
    public void loadCopies() {

        BookService bookService = new BookService();

        bookService.addCopies(TestData.allBooks.get(0).getIsbn(), 2);
        bookService.addCopies(TestData.allBooks.get(1).getIsbn(), 1);
        bookService.addCopies(TestData.allBooks.get(3).getIsbn(), 1);
        bookService.addCopies(TestData.allBooks.get(2).getIsbn(), 4);
        bookService.addCopies(TestData.allBooks.get(4).getIsbn(), 2);

    }

    @Override
    public void loadAuthors() {
        new AuthorService().save(TestData.allAuthors);
    }

    @Override
    public void loadMembers() {
        new MemberService().save(TestData.members);
    }

    @Override
    public void loadUsers() {
        new UserService().save(TestData.allUsers);
    }
}
