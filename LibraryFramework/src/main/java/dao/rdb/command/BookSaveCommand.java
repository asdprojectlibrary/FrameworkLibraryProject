package dao.rdb.command;


import dao.rdb.JDBCFacade.JDBCManager;
import model.*;

public class BookSaveCommand implements Command {
    JDBCManager jdbcManager=JDBCManager.getInstance();

    BookCopy bookCopy;
    Author author;
    Book book;

    public BookSaveCommand(Author author,Book book){
        this.author=author;
        this.book=book;
    }


    public BookSaveCommand(BookCopy bookCopy){
        this.bookCopy=bookCopy;
    }

    public BookSaveCommand(Book book){
        this.book=book;
    }

    public boolean saveBookCopy(){
        String queryDelete="delete from bookcopy where bookId="+"'"+bookCopy.getBook().getId()+"'"
                +" and copyNum="+"'"+bookCopy.getCopyNum()+"'";
        jdbcManager.deleteData(queryDelete);


        String query=" insert into bookcopy(bookId,copyNum,isAvailable) values("
                +"'"+bookCopy.getBook().getId()+"'"+","+"'"+bookCopy.getCopyNum()+"'"+","
                +"'"+1+"'"+")";
        System.out.println("Book copy saving frame: "+query);
        Integer copyId=jdbcManager.insertData(query);
        bookCopy.setId(copyId.toString());
        if(copyId==0)
            return false;
        else
            return true;

    }

    public boolean saveAutor(){
        String queryDelete="delete from bookauthor where bookId="+"'"+book.getId()+"'"
                +" and authorId="+"'"+author.getId()+"'";
        jdbcManager.deleteData(queryDelete);

        String query=" insert into bookauthor(bookId,authorId) values("
                +"'"+book.getId()+"'"+","+"'"+author.getId()+"'"+")";

        Integer bookauthorId=jdbcManager.insertData(query);

        if(bookauthorId==0)
            return false;
        else
            return true;
    }

    public boolean saveBook(){
        String title=book.getTitle().replace("'","''");
        String query=" insert into book(isbn,title,maxCheckoutLength) values("
                +"'"+book.getIsbn()+"'"+","+"'"+book.getTitle()+"'"+","
                +"'"+book.getMaxCheckoutLength()+"'"+")";

        Integer bookId=jdbcManager.insertData(query);
        book.setId(bookId.toString());
        if(bookId==0) {
            return false;
        }
        else {
            return true;
        }

    }

    @Override
    public boolean execute() {
        boolean test1=true;
        boolean test2=true;
        boolean test3=true;

        if(author!=null){
            test1=saveAutor();
        }else if(bookCopy!=null){
            test2=saveBookCopy();
        }else{
            test3=saveBook();
        }


        if(test1==false || test2==false || test3==false)
            return false;
        else
            return true;
    }

    public boolean removeAuthor(){
        String query=" delete from bookauthor where bookId="+"'"+book.getId()+"'";
        return jdbcManager.deleteData(query);
    }

    public boolean removeBookCopy(){
        String query=" delete from bookcopy where id="+"'"+bookCopy.getId()+"'";
        return jdbcManager.deleteData(query);

    }

    @Override
    public boolean undo() {
        boolean test1=true;
        boolean test2=true;

        if(author!=null){
            test1=removeAuthor();
        }
        if(bookCopy!=null){
            test2=removeBookCopy();
        }

        if(test1==true && test2==true)
            return true;
        else
            return false;
    }
}
