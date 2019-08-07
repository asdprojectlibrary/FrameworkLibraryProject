package DataAccessAdapter;

import Command.*;
import JDBCFacade.JDBCManager;
import business.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Adapter implements TargetInterface {
    private Command currentCommand;
    private Stack<Command> commandsExecuted;
    private JDBCManager jdbcManager=JDBCManager.getInstance();


    @Override
    public boolean saveLibraryMember(Member libMemb) {
        commandsExecuted=new Stack<>();
        boolean success=false;

      currentCommand=new MemberSaveCommand(libMemb.getAddress());
      if(currentCommand.execute()) {
          commandsExecuted.push(currentCommand);
          Person person=new Person(libMemb.getFirstName(),libMemb.getLastName(),libMemb.getTelephone(),libMemb.getAddress());
          currentCommand=new MemberSaveCommand(person);
          if(currentCommand.execute()) {
              commandsExecuted.push(currentCommand);

              libMemb.setId(person.getId());
              currentCommand=new MemberSaveCommand(libMemb);
              if(currentCommand.execute()){
                  success=true;
              }
          }
      }

        if(success==false){
            for(Command cmd: commandsExecuted){
                cmd.undo();
            }
        }

        return success;
    }

    @Override
    public boolean saveBook(Book book) {
        commandsExecuted=new Stack<>();
        boolean success=false;



        currentCommand=new BookSaveCommand(book);
        if(currentCommand.execute()==true){

            commandsExecuted.push(currentCommand);

            boolean testAuthor=true;
            List<Author> authors=book.getAuthors();

            for(Author aut: authors){
                currentCommand=new BookSaveCommand(aut,book);
                if(currentCommand.execute()){
                    commandsExecuted.push(currentCommand);
                }else{
                    testAuthor=false;
                    break;
                }
            }

            boolean testCopy=true;
            BookCopy[] bookCopies=book.getCopies();
            for(int i=0;i<bookCopies.length;i++){
                currentCommand=new BookSaveCommand(bookCopies[i]);
                if(currentCommand.execute()){
                    commandsExecuted.push(currentCommand);
                }else{
                    testCopy=false;
                    break;
                }
            }

            if(testAuthor==false || testCopy==false){
                for(Command cmd: commandsExecuted){
                    cmd.undo();
                }
                return false;
            }
        }else{
            System.out.println("else false");
            for(Command cmd: commandsExecuted){
                cmd.undo();
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean saveCheckoutRecord(CheckoutRecord chkOutRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYMMdd");

        Integer memberId=chkOutRecord.getMember().getId();
        for(CheckoutEntry chkEntry: chkOutRecord.getCheckoutEntries()){
            BookCopy bookCopy=chkEntry.getCheckoutItem();
            Integer bookCopyId=bookCopy.getId();
            String dueDate=chkEntry.getDueDate().format(formatter);
            String checkoutDate=chkEntry.getCheckoutDate().format(formatter);

            String query="insert into checkoutentry(memberId,bookcopyId,duedate,checkoutdate) values("
                    +"'"+memberId+"'"+","+"'"+bookCopyId+"'"+","
                    +"'"+dueDate+"'"+","+"'"+checkoutDate+"'"+")";

            Integer chkEntryId=jdbcManager.insertData(query);
            if(chkEntryId!=0){
                bookCopy.changeAvailability();
                updateBookCopy(bookCopy);
            }

        }
        return true;
    }

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    public boolean updateBookCopy(BookCopy bookCopy){
        Integer isAvailable=0;
        if(bookCopy.isAvailable())
            isAvailable=1;

        String query=" update bookcopy set isAvailable="
                +"'"+isAvailable+"'"+" where id="+"'"+bookCopy.getId()+"'";

        boolean res=jdbcManager.updateData(query);
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
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public Member searchLibraryMemberById(Integer memberId) {
        String query="select * from libraryMember as li,person as p,address as ad " +
                " where li.idPerson=p.idPerson and p.idAddress=ad.idAddress and memberId="+"'"+memberId+"'";


        HashMap<String,Object> rs=jdbcManager.selection(query).get(0);

        Address add=new Address((String) rs.get("street"),(String) rs.get("city"),(String) rs.get("state"),(String) rs.get("zipCode"));
        Integer idMem=(Integer) rs.get("memberId");
        String fname=(String) rs.get("firstName");
        String lName=(String) rs.get("lastName");
        String phone=(String) rs.get("telephone");
        Member libMemb=new Member(idMem,fname,lName,phone,add);
        return libMemb;
    }

    @Override
    public List<Member> searchAllLibraryMember() {
        List<Member> listAllMember=new ArrayList<>();
        String query="select * from libraryMember as li,person as p,address as ad" +
                " where li.idPerson=p.idPerson and p.idAddress=ad.idAddress";


        List<HashMap<String,Object>> listMemb=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: listMemb){
            Address add=new Address((String) rs.get("street"),(String) rs.get("city"),(String) rs.get("state"),(String) rs.get("zipCode"));
            Integer idMem=(Integer) rs.get("memberId");
            String fname=(String) rs.get("firstName");
            String lName=(String) rs.get("lastName");
            String phone=(String) rs.get("telephone");
            Member libMemb=new Member(idMem,fname,lName,phone,add);
            listAllMember.add(libMemb);
        }

        return listAllMember;
    }

    @Override
    public Book searchBookByISBN(String isbn) {
        Book book=null;

        String query="select * from book " +
                " where isbn="+"'"+isbn+"'";

        List<HashMap<String,Object>> liMap=jdbcManager.selection(query);

        if(!liMap.isEmpty()){
            HashMap<String,Object> rs=liMap.get(0);


            Integer bookId=(Integer) rs.get("bookId");
            String visbn=(String) rs.get("isbn");
            String title=(String) rs.get("title");
            Integer maxCheckoutLength=(Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors=searchBookAuthors(bookId);
            book=new Book(visbn,title,maxCheckoutLength,liAuthors);
            book.setId(bookId);
            searchBookCopies(book);
        }

        return book;

    }

    public List<Author> searchBookAuthors(Integer bookId){
        List<Author> authors=new ArrayList<>();
        String query="select * from (select id, ba.bookId, au.authorId, bio, p.idPerson, firstName, lastName, telephone, idAddress\n" +
                " from bookauthor as ba,author as au,person as p\n" +
                "where ba.authorId=au.authorId and p.idPerson=au.idPerson and bookId="+"'"+bookId+"'"+") tb left join address as ad\n" +
                "on tb.idAddress=ad.idAddress";


        List<HashMap<String,Object>> listAuthors=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: listAuthors){
            Address add=new Address((String) rs.get("street"),(String) rs.get("city"),(String) rs.get("state"),(String) rs.get("zipCode"));
            Integer authorId=(Integer) rs.get("authorId");
            String fname=(String) rs.get("firstName");
            String lName=(String) rs.get("lastName");
            String phone=(String) rs.get("telephone");
            String bio=(String) rs.get("bio");
            Author author=new Author(fname,lName,phone,add,bio);
            author.setId(authorId);
            authors.add(author);
        }

        return authors;
    }

    public BookCopy[] searchBookCopies(Book book){
        List<BookCopy> bookCopies=new ArrayList<>();


        String query="select id, copyNum, isAvailable, bo.bookId, isbn, title, maxCheckoutLength\n" +
                "from bookCopy as bc,book as bo where bc.bookId=bo.bookId and bo.bookId="+"'"+book.getId()+"'";


        List<HashMap<String,Object>> listCopies=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: listCopies){
            Integer copyNum=(Integer) rs.get("copyNum");
            Integer copyId=(Integer) rs.get("id");
            String visbn=(String) rs.get("isbn");
            String title=(String) rs.get("title");
            boolean isAvailable=(boolean) rs.get("isAvailable");

            BookCopy bookCopy=new BookCopy(book,copyNum,isAvailable);
            bookCopy.setId(copyId);
            bookCopies.add(bookCopy);
        }

        BookCopy[] bookCopies2= new BookCopy[bookCopies.size()];
        int i=0;
        for(BookCopy b: bookCopies){
            bookCopies2[i]=b;
            i++;
        }
        book.setCopies(bookCopies2);
        return bookCopies2;
    }

    @Override
    public List<Book> searchAllBook() {
        List<Book> ListBooks=new ArrayList<>();
        String query="select * from book ";

        List<HashMap<String,Object>> liMap=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: liMap){

            Integer bookId=(Integer) rs.get("bookId");
            String visbn=(String) rs.get("isbn");
            String title=(String) rs.get("title");
            Integer maxCheckoutLength=(Integer) rs.get("maxCheckoutLength");
            List<Author> liAuthors=searchBookAuthors(bookId);
            Book book=new Book(visbn,title,maxCheckoutLength,liAuthors);
            book.setId(bookId);
            searchBookCopies(book);
            ListBooks.add(book);
        }
        return ListBooks;
    }

    @Override
    public List<BookCopy> searchBookCopies(String isbn) {
        return Arrays.asList(searchBookByISBN(isbn).getCopies());
    }

    @Override
    public CheckoutRecord searchCheckoutRecordForMember(Integer memberId) {

        String query="select id, bookcopyId, memberId, duedate, checkoutdate from checkoutentry where memberId="+"'"+memberId+"'";
        Member member=searchLibraryMemberById(memberId);
        CheckoutRecord checkoutRecord=new CheckoutRecord(member);

        List<HashMap<String,Object>> listEntries=jdbcManager.selection(query);
        for(HashMap<String,Object> rs: listEntries){
            Integer entryId=(Integer) rs.get("id");
            Integer bookCopyId=(Integer) rs.get("bookcopyId");


            Date date1=(Date) rs.get("duedate");
            Date date2=(Date) rs.get("checkoutdate");
            ZoneId systemDefault = ZoneId.systemDefault();
            ZonedDateTime dueDate=ZonedDateTime.ofInstant(date1.toInstant(), systemDefault);
            ZonedDateTime checkoutDate=ZonedDateTime.ofInstant(date2.toInstant(), systemDefault);

            BookCopy bookCopy=searchBookCopyById(bookCopyId);
            checkoutRecord.addCheckoutEntry(bookCopy,dueDate,checkoutDate);

        }




        return checkoutRecord;
    }

    @Override
    public List<Author> searchAllAuthors() {
        List<Author> authors=new ArrayList<>();
        String query="select * from (select au.authorId, bio, p.idPerson, firstName, lastName, telephone, idAddress" +
                " from author as au,person as p " +
                " where p.idPerson=au.idPerson ) tb left join address as ad " +
                " on tb.idAddress=ad.idAddress";


        List<HashMap<String,Object>> listAuthors=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: listAuthors){
            Address add=new Address((String) rs.get("street"),(String) rs.get("city"),(String) rs.get("state"),(String) rs.get("zipCode"));
            Integer authorId=(Integer) rs.get("authorId");
            String fname=(String) rs.get("firstName");
            String lName=(String) rs.get("lastName");
            String phone=(String) rs.get("telephone");
            String bio=(String) rs.get("bio");
            Author author=new Author(fname,lName,phone,add,bio);
            author.setId(authorId);
            authors.add(author);
        }

        return authors;
    }

    public BookCopy searchBookCopyById(Integer bookCopyId){
        BookCopy bookCopy=null;


        String query="select id, copyNum, isAvailable, bo.bookId, isbn, title, maxCheckoutLength\n" +
                "from bookCopy as bc,book as bo where bc.bookId=bo.bookId and id="+"'"+bookCopyId+"'";


        List<HashMap<String,Object>> listCopies=jdbcManager.selection(query);

        for(HashMap<String,Object> rs: listCopies){
            Integer copyNum=(Integer) rs.get("copyNum");
            Integer copyId=(Integer) rs.get("id");
            String visbn=(String) rs.get("isbn");
            String title=(String) rs.get("title");
            boolean isAvailable=(boolean) rs.get("isAvailable");
            String isbn=(String) rs.get("isbn");
            Book book=searchBookByISBN(isbn);

            bookCopy=new BookCopy(book,copyNum,isAvailable);
            bookCopy.setId(copyId);

        }


        return bookCopy;
    }
}
