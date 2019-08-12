package dataaccess;

import config.DataLoader;
import service.AuthorService;
import service.BookService;
import service.MemberService;
import service.UserService;

public class DataLoaderImpl extends DataLoader {

    @Override
    public void loadBooks() {
        try {
            new service.BookService().save(TestData.allBooks);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void loadCopies() {

        try {
            BookService bookService = new BookService();

            bookService.addCopies(TestData.allBooks.get(0).getIsbn(), 2);
            bookService.addCopies(TestData.allBooks.get(1).getIsbn(), 1);
            bookService.addCopies(TestData.allBooks.get(3).getIsbn(), 1);
            bookService.addCopies(TestData.allBooks.get(2).getIsbn(), 4);
            bookService.addCopies(TestData.allBooks.get(4).getIsbn(), 2);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void loadAuthors() {
        try {
            new AuthorService().save(TestData.allAuthors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void loadMembers() {
        try {
            new MemberService().save(TestData.members);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void loadUsers() {
        try {
            new UserService().save(TestData.allUsers);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
