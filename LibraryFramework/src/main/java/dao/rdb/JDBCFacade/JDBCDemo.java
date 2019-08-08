package dao.rdb.JDBCFacade;

import dao.rdb.dataaccessadapter.*;
import model.CheckoutEntry;

public class JDBCDemo {

    public static void main(String[] args) {
        JDBCManager jdbMan=JDBCManager.getInstance();
        TargetInterface adapter=new Adapter();

        //-------test get entry-------
        CheckoutEntry ent=adapter.searchCheckoutEntryById("5");
        System.out.println(ent);

        //--------save checkoutEntry---------
        /*Book book=adapter.searchBookByISBN("690");
        BookCopy bookCopy1=null;
        try{
            bookCopy1=book.getNextAvailableCopy();
        }catch (BookCopyNotAvailable ex){System.out.println("No copy available");}
        Member member=adapter.searchLibraryMemberById(1);
        ZonedDateTime dueDate=ZonedDateTime.now();
        ZonedDateTime chkoutDate=ZonedDateTime.now();
        CheckoutEntry checkoutEntry=new CheckoutEntry(bookCopy1,member,dueDate,chkoutDate);
        adapter.save(checkoutEntry);*/

        //--------test search for all checkoutrecords----
        //List<CheckoutRecord> liRec=adapter.searchAllCheckoutRecord();
        //System.out.println(liRec);


        //-------test search for member-----
        //Member member=adapter.searchLibraryMemberById(1);
        //System.out.println(member);

        //-------test list of author--------------
        /*List<Author> lisAuthor=adapter.searchAllAuthors();
        System.out.println(lisAuthor);*/

        //-------test save checkoutrecord----------
        /*Book book=adapter.searchBookByISBN("690");
        BookCopy bookCopy1=null;
        try{
            bookCopy1=book.getNextAvailableCopy();
        }catch (BookCopyNotAvailable ex){System.out.println("No copy available");}

        Member member=adapter.searchLibraryMemberById(1);
        ZonedDateTime dueDate=ZonedDateTime.now();
        ZonedDateTime chkoutDate=ZonedDateTime.now();
        CheckoutRecord chkRec=new CheckoutRecord(member);
        chkRec.addCheckoutEntry(bookCopy1,chkoutDate,dueDate);

        adapter.saveCheckoutRecord(chkRec);*/





        //-------test search book------------
        //Book book=adapter.searchBookByISBN("690");
        //System.out.println(book);

        //List<Book> listBooks=adapter.searchAllBook();
        //listBooks.forEach(x->System.out.println(x));

        //-------test saving a book--------------
        /*Address add=new Address("street","city","state","32334");
        Author author2=new Author("Jean","Peter","765456734",add,"Born in Haiti and author of ten books");
        Author author1=new Author("Jean","Peter","765456734",add,"Born in Haiti and author of ten books");
        author1.setAuthorId(1);
        author2.setAuthorId(2);

        List<Author> authors=new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        Book book=new Book("690","WebMaster 2",10,authors,3);
        adapter.saveBook(book);*/



        //-------test retreive all libraryMember----------
        /*
        List<LibraryMember> allMember=adapter.searchAllLibraryMember();
        System.out.println(allMember);*/

        /*Address add1=new Address("100, imp loe","Fairfield","Iowa","55256");
        LibraryMember libMemb=new LibraryMember(0,"Paul","Steeve","8638524756",add1);
        adapter.saveLibraryMember(libMemb);*/

       /* List<HashMap<String,Object>> result=jdbMan.selection("Select * from person");

        //-----printing result------------

        //result.forEach(x->System.out.println(x.keySet()));
        for(HashMap<String,Object> st: result){
            Integer idPerson=(Integer) st.get("idPerson");
            System.out.println(idPerson);
        }*/
/*
        //------Insert data-----------
        Integer resInsert=jdbMan.insertData("insert into test(firstName,lastName) values('Jean','Paul')");
        if(resInsert!=0){
            System.out.println("Insertion successful"+resInsert);
        }*/




        /*Address add1=new Address("100, imp loe","Fairfield","Iowa","55256");
        LibraryMember libMemb=new LibraryMember("","Paul","Steeve","8638524756",add1);
        Adapter adapter=new Adapter();
        adapter.saveLibraryMember(libMemb);*/

        //Integer resInsert=JDBCManager.getInstance().insertData("insert into person(firstName,lastName,telephone,idAddress) values('Paul','Steeve','8638524756','3')");
        //System.out.println(resInsert);
    }
}
