package dao.rdb.dataaccessadapter;

import config.LibraryManager;
import config.MysqlConfig;
import dao.rdb.command.BookSaveCommand;
import dao.rdb.command.Command;
import dao.rdb.command.MemberSaveCommand;
import model.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBAdapter implements DBTarget {

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

        commandsExecuted=new Stack<>();
        boolean success=false;
        boolean testBook=false;
        if(book==null)
            return false;


        if(searchBookByISBN(book.getIsbn())!=null){
            testBook=true;
        }else{
            currentCommand=new BookSaveCommand(book);
            testBook=currentCommand.execute();

        }


        if(testBook){

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
            for (BookCopy copy : bookCopies) {
                currentCommand = new BookSaveCommand(copy);
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
    public boolean save(CheckoutEntry checkoutEntry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYMMdd");

        String memberId = checkoutEntry.getMember().getId();

        BookCopy bookCopy = checkoutEntry.getCheckoutItem();
        String bookCopyId = bookCopy.getId();
        String dueDate = checkoutEntry.getDueDate().format(formatter);
        String checkoutDate = checkoutEntry.getCheckoutDate().format(formatter);

        String query = "insert into checkoutentry(memberId,bookcopyId,duedate,checkoutdate) values("
                + "'" + memberId + "'" + "," + "'" + bookCopyId + "'" + ","
                + "'" + dueDate + "'" + "," + "'" + checkoutDate + "'" + ")";

        Integer chkEntryId = jdbcManager.insertData(query);
        if (chkEntryId != 0) {
            bookCopy.setAvailable(false);
            updateBookCopy(bookCopy);
        }


        return true;
    }

    @Override
    public boolean save(Author author) {

        String query = " insert into author(bio,idPerson) values("
                + "'" + author.getBio() + "'" + "," + "'" + author.getId() + "'" + ")";

        Integer authorId = jdbcManager.insertData(query);

        if (authorId == 0)
            return false;
        else
            return true;

    }

    @Override
    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYMMdd");

        String memberId = chkOutRecord.getMember().getId();
        for (CheckoutEntry chkEntry : chkOutRecord.getCheckoutEntries()) {
            BookCopy bookCopy = chkEntry.getCheckoutItem();
            String bookCopyId = bookCopy.getId();
            String dueDate = chkEntry.getDueDate().format(formatter);
            String checkoutDate = chkEntry.getCheckoutDate().format(formatter);

            String query = "insert into checkoutentry(memberId,bookcopyId,duedate,checkoutdate) values("
                    + "'" + memberId + "'" + "," + "'" + bookCopyId + "'" + ","
                    + "'" + dueDate + "'" + "," + "'" + checkoutDate + "'" + ")";

            Integer chkEntryId = jdbcManager.insertData(query);
            if (chkEntryId != 0) {
                bookCopy.setAvailable(false);
                updateBookCopy(bookCopy);
            }

        }
        return true;
    }


    public boolean updateBookCopy(BookCopy bookCopy) {
        Integer isAvailable = 0;
        if (bookCopy.isAvailable())
            isAvailable = 1;

        String query = " update bookcopy set isAvailable="
                + "'" + isAvailable + "'" + " where id=" + "'" + bookCopy.getId() + "'";

        boolean res = jdbcManager.updateData(query);
        return res;
    }

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

    @Override
    public Member searchLibraryMemberById(String memberId) {

        String query = "select * from libraryMember as li,person as p,address as ad " +
                " where li.idPerson=p.idPerson and p.idAddress=ad.idAddress and memberId=" + "'" + memberId + "'";


        HashMap<String, Object> rs = jdbcManager.selection(query).get(0);

        Address add = new Address((String) rs.get("street"), (String) rs.get("city"), (String) rs.get("state"), (String) rs.get("zipCode"));
        String idMem = rs.get("memberId").toString();
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
            String idMem = rs.get("memberId").toString();
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


            String bookId = rs.get("bookId").toString();
            String visbn = rs.get("isbn").toString();
            String title = (String) rs.get("title");
            Integer maxCheckoutLength = (Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors = searchBookAuthors(bookId);
            book = new Book(visbn, title, maxCheckoutLength, liAuthors);
            book.setId(bookId);
            searchBookCopies(book);
        }

        return book;

    }

    @Override
    public CheckoutEntry searchCheckoutEntryById(String id) {
        String query = "select id, bookcopyId, memberId, duedate, checkoutdate from checkoutentry where id=" + "'" + id + "'";
        CheckoutEntry checkoutEntry = null;

        List<HashMap<String, Object>> listEntries = jdbcManager.selection(query);
        for (HashMap<String, Object> rs : listEntries) {
            String entryId = rs.get("id").toString();
            Integer bookCopyId = (Integer) rs.get("bookcopyId");
            String memberId = rs.get("memberId").toString();
            Member member = searchLibraryMemberById(memberId);

            Date date1 = (Date) rs.get("duedate");
            Date date2 = (Date) rs.get("checkoutdate");
            ZoneId systemDefault = ZoneId.systemDefault();
            ZonedDateTime dueDate = ZonedDateTime.ofInstant(date1.toInstant(), systemDefault);
            ZonedDateTime checkoutDate = ZonedDateTime.ofInstant(date2.toInstant(), systemDefault);

            BookCopy bookCopy = searchBookCopyById(bookCopyId);
            checkoutEntry.setCheckoutItem(bookCopy);
            checkoutEntry.setDueDate(dueDate);
            checkoutEntry.setCheckoutDate(checkoutDate);
            checkoutEntry.setMember(member);
            checkoutEntry.setId(entryId);


        }


        return checkoutEntry;
    }


    @Override
    public List<CheckoutEntry> searchAllCheckoutEntry() {
        String query = "select id, bookcopyId, memberId, duedate, checkoutdate from checkoutentry";
        List<CheckoutEntry> listChkEntry = new ArrayList<>();

        List<HashMap<String, Object>> listEntries = jdbcManager.selection(query);
        for (HashMap<String, Object> rs : listEntries) {
            String entryId = rs.get("id").toString();
            Integer bookCopyId = (Integer) rs.get("bookcopyId");
            String memberId = rs.get("memberId").toString();
            Member member = searchLibraryMemberById(memberId);

            Date date1 = (Date) rs.get("duedate");
            Date date2 = (Date) rs.get("checkoutdate");
            ZoneId systemDefault = ZoneId.systemDefault();
            ZonedDateTime dueDate = ZonedDateTime.ofInstant(date1.toInstant(), systemDefault);
            ZonedDateTime checkoutDate = ZonedDateTime.ofInstant(date2.toInstant(), systemDefault);

            BookCopy bookCopy = searchBookCopyById(bookCopyId);
            CheckoutEntry checkoutEntry = new CheckoutEntry();
            checkoutEntry.setCheckoutItem(bookCopy);
            checkoutEntry.setDueDate(dueDate);
            checkoutEntry.setCheckoutDate(checkoutDate);
            checkoutEntry.setMember(member);
            checkoutEntry.setId(entryId);

            listChkEntry.add(checkoutEntry);
        }
        return listChkEntry;
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
            String authorId = rs.get("authorId").toString();
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

    public List<BookCopy> searchBookCopies(Book book) {
        List<BookCopy> bookCopies = new ArrayList<>();


        String query = "select id, copyNum, isAvailable, bo.bookId, isbn, title, maxCheckoutLength\n" +
                "from bookCopy as bc,book as bo where bc.bookId=bo.bookId and bo.bookId=" + "'" + book.getId() + "'";


        List<HashMap<String, Object>> listCopies = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listCopies) {
            Integer copyNum = (Integer) rs.get("copyNum");
            String copyId = rs.get("id").toString();
            String visbn = (String) rs.get("isbn");
            String title = (String) rs.get("title");
            boolean isAvailable = (boolean) rs.get("isAvailable");

            BookCopy bookCopy = new BookCopy(book, copyNum, isAvailable);
            bookCopy.setId(copyId);
            bookCopies.add(bookCopy);
        }


        book.setCopies(bookCopies);
        return bookCopies;
    }

    @Override
    public List<Book> searchAllBook() {
        List<Book> ListBooks = new ArrayList<>();
        String query = "select * from book ";

        List<HashMap<String, Object>> liMap = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : liMap) {

            String bookId = rs.get("bookId").toString();
            String visbn = rs.get("isbn").toString();
            String title = (String) rs.get("title");
            Integer maxCheckoutLength = (Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors = searchBookAuthors(bookId);
            //System.out.println("Null value : "+bookId+" / "+liAuthors);
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
    public List<CheckoutRecord> searchAllCheckoutRecord() {
        String query = "select * from libraryMember";

        List<CheckoutRecord> checkoutRecords = new ArrayList<>();

        List<HashMap<String, Object>> allMembers = jdbcManager.selection(query);
        for (HashMap<String, Object> rs : allMembers) {
            String memberId = rs.get("memberId").toString();
            CheckoutRecord checkoutRecord = searchCheckoutRecordForMember(memberId);

            checkoutRecords.add(checkoutRecord);

        }


        return checkoutRecords;
    }


    public CheckoutRecord searchCheckoutRecordForMember(String memberId) {

        String query = "select id, bookcopyId, memberId, duedate, checkoutdate from checkoutentry where memberId=" + "'" + memberId + "'";
        Member member = searchLibraryMemberById(memberId);
        CheckoutRecord checkoutRecord = new CheckoutRecord();
        checkoutRecord.setMember(member);
        List<CheckoutEntry> listEntry = new ArrayList<>();

        List<HashMap<String, Object>> mapEntry = jdbcManager.selection(query);
        for (HashMap<String, Object> rs : mapEntry) {
            String entryId = rs.get("id").toString();
            Integer bookCopyId = (Integer) rs.get("bookcopyId");


            Date date1 = (Date) rs.get("duedate");
            Date date2 = (Date) rs.get("checkoutdate");
            ZoneId systemDefault = ZoneId.systemDefault();
            ZonedDateTime dueDate = ZonedDateTime.ofInstant(date1.toInstant(), systemDefault);
            ZonedDateTime checkoutDate = ZonedDateTime.ofInstant(date2.toInstant(), systemDefault);

            BookCopy bookCopy = searchBookCopyById(bookCopyId);
            CheckoutEntry chkEntry = new CheckoutEntry();
            chkEntry.setCheckoutItem(bookCopy);
            chkEntry.setDueDate(dueDate);
            chkEntry.setCheckoutDate(checkoutDate);
            chkEntry.setMember(member);
            chkEntry.setId(entryId);
            listEntry.add(chkEntry);


        }


        checkoutRecord.setCheckoutEntries(listEntry);
        return checkoutRecord;
    }

    @Override
    public List<Author> searchAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "select * from (select au.authorId, bio, p.idPerson, firstName, lastName, telephone, idAddress" +
                " from author as au,person as p " +
                " where p.idPerson=au.idPerson ) tb left join address as ad " +
                " on tb.idAddress=ad.idAddress";


        List<HashMap<String, Object>> listAuthors = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listAuthors) {
            Address add = new Address((String) rs.get("street"), (String) rs.get("city"), (String) rs.get("state"), (String) rs.get("zipCode"));
            String authorId = rs.get("authorId").toString();
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


    public BookCopy searchBookCopyById(Integer bookCopyId) {
        BookCopy bookCopy = null;


        String query = "select id, copyNum, isAvailable, bo.bookId, isbn, title, maxCheckoutLength\n" +
                "from bookCopy as bc,book as bo where bc.bookId=bo.bookId and id=" + "'" + bookCopyId + "'";


        List<HashMap<String, Object>> listCopies = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : listCopies) {
            Integer copyNum = (Integer) rs.get("copyNum");
            String copyId = rs.get("id").toString();
            String visbn = (String) rs.get("isbn");
            String title = (String) rs.get("title");
            boolean isAvailable = (boolean) rs.get("isAvailable");
            String isbn = (String) rs.get("isbn");
            Book book = searchBookByISBN(isbn);

            bookCopy = new BookCopy(book, copyNum, isAvailable);
            bookCopy.setId(copyId);

        }


        return bookCopy;
    }

    @Override
    public User searchUserWithPWD(String userId, String password) {

        String query = "select usr.id, usr.userId, FullName, password, permission from user usr,permission per where usr.userId=per.userId "
                + "and usr.userId=" + "'" + userId + "'" + " and password=" + "'" + password + "'";

        User user = null;
        String fullName = "";
        String id = "";
        boolean exists = false;

        Auth permissions = null;


        List<HashMap<String, Object>> userInfo = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : userInfo) {
            exists = true;
            id = rs.get("id").toString();
            fullName = (String) rs.get("FullName");
            String perm = (String) rs.get("permission");
            if (perm.equals("librarian")) {
                permissions = Auth.LIBRARIAN;
            } else if (perm.equals("admin")) {
                permissions = Auth.ADMIN;
            } else if (perm.equals("both")) {
                permissions = Auth.BOTH;
            }
        }

        if (exists) {
            user = new User();
            user.setPassword(password);
            user.setAuthorization(permissions);
        }

        return user;
    }

    public boolean runScript(String filePath) {
        boolean testResult = true;
        jdbcManager.runScript(filePath);

        return testResult;
    }

    @Override
    public boolean createTables() {

        MysqlConfig mysqlConfig = (MysqlConfig) LibraryManager.getInstance().getConfig();

        boolean testResult=true;
        //jdbcManager.runScript("src/main/java/config/libraryDataBase.sql");
        jdbcManager.runScript(mysqlConfig.getScriptPath()+"libraryDataBase.sql");

        /*String s= new String();
        StringBuffer sb = new StringBuffer();
        String dateBaseToUse="USE "+dataBaseName+";";
        sb.append(dateBaseToUse);


            try{
                FileReader fr = new FileReader(new File("src/main/resources/libraryDataBase.sql"));

                BufferedReader br = new BufferedReader(fr);
                while((s = br.readLine()) != null){
                    sb.append(s);
                }
                br.close();

                String[] inst = sb.toString().split(";");

                for(int i = 0; i<inst.length; i++){
                    if(!inst[i].trim().equals("")){
                        jdbcManager.updateData(inst[i]);
                        System.out.println(">>"+inst[i]);
                    }
                }
            }catch(Exception ex){System.out.println(ex.getMessage());}

        }else{
            testResult=false;
            System.out.println("Cannot create Database");
        }*/

        return testResult;
    }

    @Override
    public User searchUser(String userId) {
        System.out.println("yes");
        String query = "select usr.id, usr.userId, FullName, password, permission from user usr,permission per where usr.userId=per.userId "
                + "and usr.userId=" + "'" + userId + "'";

        User user = null;
        String fullName = "";
        String id = "";
        String pwd = "";
        boolean exists = false;

        Auth permissions = null;


        List<HashMap<String, Object>> userInfo = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : userInfo) {
            exists = true;
            id = rs.get("id").toString();
            fullName = (String) rs.get("FullName");
            pwd = (String) rs.get("password");
            String perm = (String) rs.get("permission");
            if (perm.equals("librarian")) {
                permissions = Auth.LIBRARIAN;
            } else if (perm.equals("admin")) {
                permissions = Auth.ADMIN;
            } else if (perm.equals("both")) {
                permissions = Auth.BOTH;
            }
        }

        if (exists) {
            user = new User();
            user.setPassword(pwd);
            user.setAuthorization(permissions);
        }

        return user;
    }


    @Override
    public List<User> searchAllUsers() {
        System.out.println("yes");
        String query = "select usr.id, usr.userId, FullName, password, permission from user usr,permission per where usr.userId=per.userId ";
        List<User> listUser = new ArrayList<>();

        User user = null;
        String fullName = "";
        String id = "";
        String pwd = "";
        boolean exists = false;

        Auth permissions = null;


        List<HashMap<String, Object>> userInfo = jdbcManager.selection(query);

        for (HashMap<String, Object> rs : userInfo) {
            exists = true;
            id = rs.get("id").toString();
            fullName = (String) rs.get("FullName");
            pwd = (String) rs.get("password");
            String perm = (String) rs.get("permission");
            if (perm.equals("librarian")) {
                permissions = Auth.LIBRARIAN;
            } else if (perm.equals("admin")) {
                permissions = Auth.ADMIN;
            } else if (perm.equals("both")) {
                permissions = Auth.BOTH;
            }
            user = new User();
            user.setPassword(pwd);
            user.setAuthorization(permissions);
            listUser.add(user);
        }


        return listUser;
    }
}
