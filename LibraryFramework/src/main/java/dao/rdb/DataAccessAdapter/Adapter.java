package dao.rdb.DataAccessAdapter;

import dao.rdb.Command.BookSaveCommand;
import dao.rdb.Command.Command;
import dao.rdb.Command.MemberSaveCommand;
import dao.rdb.JDBCFacade.JDBCManager;
import model.*;

import java.util.*;

public class Adapter implements TargetInterface {
    private Command currentCommand;
    private Stack<Command> commandsExecuted;
    private JDBCManager jdbcManager = JDBCManager.getInstance();


    @Override
    public boolean save(Member libMemb) {
        commandsExecuted = new Stack<>();
        boolean success = false;

        currentCommand = new MemberSaveCommand(libMemb.getAddress());
        if (currentCommand.execute()) {
            commandsExecuted.push(currentCommand);
            Person person = new Person(libMemb.getFirstName(), libMemb.getLastName(), libMemb.getTelephone(), libMemb.getAddress());
            currentCommand = new MemberSaveCommand(person);
            if (currentCommand.execute()) {
                commandsExecuted.push(currentCommand);

                libMemb.setId(person.getId());
                currentCommand = new MemberSaveCommand(libMemb);
                if (currentCommand.execute()) {
                    success = true;
                }
            }
        }

        if (success == false) {
            for (Command cmd : commandsExecuted) {
                cmd.undo();
            }
        }

        return success;
    }

    @Override
    public boolean save(Book book) {
        commandsExecuted = new Stack<>();
        boolean success = false;


        currentCommand = new BookSaveCommand(book);
        if (currentCommand.execute() == true) {

            commandsExecuted.push(currentCommand);

            boolean testAuthor = true;
            List<Author> authors = book.getAuthors();

            for (Author aut : authors) {
                currentCommand = new BookSaveCommand(aut, book);
                if (currentCommand.execute()) {
                    commandsExecuted.push(currentCommand);
                } else {
                    testAuthor = false;
                    break;
                }
            }

            boolean testCopy = true;
            List<BookCopy> bookCopies = book.getCopies();
            for (int i = 0; i < bookCopies.size(); i++) {
                currentCommand = new BookSaveCommand(bookCopies.get(i));
                if (currentCommand.execute()) {
                    commandsExecuted.push(currentCommand);
                } else {
                    testCopy = false;
                    break;
                }
            }

            if (testAuthor == false || testCopy == false) {
                for (Command cmd : commandsExecuted) {
                    cmd.undo();
                }
                return false;
            }
        } else {
            System.out.println("else false");
            for (Command cmd : commandsExecuted) {
                cmd.undo();
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord) {
        return false;
    }

    /*@Override
    public boolean saveUser(User user) {
        return false;
    }*/

    @Override
    public boolean updateLibraryMember(Member libraryMember) {
        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        return false;
    }

    @Override
    public boolean updateCheckoutRecord(CheckoutRecord chkOutRecord) {
        return false;
    }

    /*@Override
    public boolean updateUser(User user) {
        return false;
    }*/

    @Override
    public Member searchLibraryMemberById(String memberId) {
        String query = "select * from libraryMember as li,person as p,address as ad " +
                " where li.idPerson=p.idPerson and p.idAddress=ad.idAddress and memberId=" + "'" + memberId + "'";


        HashMap<String, Object> rs = jdbcManager.selection(query).get(1);

        Address add = new Address((String) rs.get("street"), (String) rs.get("city"), (String) rs.get("state"), (String) rs.get("zipCode"));
        String idMem = (String) rs.get("memberId");
        String fname = (String) rs.get("firstName");
        String lName = (String) rs.get("lastName");
        String phone = (String) rs.get("telephone");
        Member libMemb = new Member(idMem, fname, lName, phone, add);

        return libMemb;
    }

    @Override
    public List<Member> searchAllLibraryMember() {
        List<Member> listAllMember = new ArrayList<>();
        String query = "select * from libraryMember as li,person as p,address as ad" +
                " where li.idPerson=p.idPerson and p.idAddress=ad.idAddress";


        List<HashMap<String, Object>> listMemb = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listMemb) {
            Address add = new Address((String) rs.get("street"), (String) rs.get("city"), (String) rs.get("state"), (String) rs.get("zipCode"));
            String idMem = (String) rs.get("memberId");
            String fname = (String) rs.get("firstName");
            String lName = (String) rs.get("lastName");
            String phone = (String) rs.get("telephone");
            Member libMemb = new Member(idMem, fname, lName, phone, add);
            listAllMember.add(libMemb);
        }

        return listAllMember;
    }

    @Override
    public Book searchBookByISBN(String isbn) {
        Book book = null;

        String query = "select * from book " +
                " where isbn=" + "'" + isbn + "'";

        List<HashMap<String, Object>> liMap = jdbcManager.selection(query);

        if (!liMap.isEmpty()) {
            HashMap<String, Object> rs = liMap.get(0);


            String bookId = (String) rs.get("bookId");
            String visbn = (String) rs.get("isbn");
            String title = (String) rs.get("title");
            Integer maxCheckoutLength = (Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors = searchBookAuthors(bookId);
            book = new Book(visbn, title, maxCheckoutLength, liAuthors);
            book.setId(bookId);
            searchBookCopies(book);
        }

        return book;

    }

    public List<Author> searchBookAuthors(String bookId) {
        List<Author> authors = new ArrayList<>();
        String query = "select * from (select id, ba.bookId, au.authorId, bio, p.idPerson, firstName, lastName, telephone, idAddress\n" +
                " from bookauthor as ba,author as au,person as p\n" +
                "where ba.authorId=au.authorId and p.idPerson=au.idPerson and bookId=" + "'" + bookId + "'" + ") tb left join address as ad\n" +
                "on tb.idAddress=ad.idAddress";


        List<HashMap<String, Object>> listAuthors = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listAuthors) {
            Address add = new Address((String) rs.get("street"), (String) rs.get("city"), (String) rs.get("state"), (String) rs.get("zipCode"));
            String authorId = (String) rs.get("authorId");
            String fname = (String) rs.get("firstName");
            String lName = (String) rs.get("lastName");
            String phone = (String) rs.get("telephone");
            String bio = (String) rs.get("bio");
            Author author = new Author(fname, lName, phone, add, bio);
            author.setId(authorId);
            authors.add(author);
        }

        return authors;
    }

    public BookCopy[] searchBookCopies(Book book) {
        List<BookCopy> bookCopies = new ArrayList<>();


        String query = "select id, copyNum, isAvailable, bo.bookId, isbn, title, maxCheckoutLength\n" +
                "from bookCopy as bc,book as bo where bc.bookId=bo.bookId and bo.bookId=" + "'" + book.getId() + "'";


        List<HashMap<String, Object>> listCopies = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listCopies) {
            Integer copyNum = (Integer) rs.get("copyNum");
            String copyId = (String) rs.get("id");
            String visbn = (String) rs.get("isbn");
            String title = (String) rs.get("title");
            boolean isAvailable = (boolean) rs.get("isAvailable");

            BookCopy bookCopy = new BookCopy(book, copyNum, isAvailable);
            bookCopy.setId(copyId);
            bookCopies.add(bookCopy);
        }

        BookCopy[] bookCopies2 = new BookCopy[bookCopies.size()];
        int i = 0;
        for (BookCopy b : bookCopies) {
            bookCopies2[i] = b;
            i++;
        }

        book.setCopies(Arrays.asList(bookCopies2));
        return bookCopies2;
    }

    @Override
    public List<Book> searchAllBook() {
        List<Book> ListBooks = new ArrayList<>();
        String query = "select * from book ";

        List<HashMap<String, Object>> liMap = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : liMap) {

            String bookId = (String) rs.get("bookId");
            String visbn = (String) rs.get("isbn");
            String title = (String) rs.get("title");
            Integer maxCheckoutLength = (Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors = searchBookAuthors(bookId);
            Book book = new Book(visbn, title, maxCheckoutLength, liAuthors);
            book.setId(bookId);
            searchBookCopies(book);
            ListBooks.add(book);
        }
        return ListBooks;
    }

    @Override
    public List<BookCopy> searchBookCopies(String isbn) {
        return searchBookByISBN(isbn).getCopies();
    }

    @Override
    public List<CheckoutRecord> searchAllCheckoutRecord(Integer memberId) {
        return null;
    }
}
