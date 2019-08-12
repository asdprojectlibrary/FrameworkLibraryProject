package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import service.AuthorService;
import service.BookService;
import service.MemberService;
import service.UserService;
import model.*;


public class TestData {

	static List<Member> members = new ArrayList<Member>();
	@SuppressWarnings("serial")

	static List<Address> addresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};
	@SuppressWarnings("serial")
	static List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("Joe", "Thomas", "641-445-2123", addresses.get(0), "A happy man is he."));
			add(new Author("Sandra", "Thomas", "641-445-2123", addresses.get(0), "A happy wife is she."));
			add(new Author("Nirmal", "Pugh", "641-919-3223", addresses.get(1), "Thinker of thoughts."));
			add(new Author("Andrew", "Cleveland", "976-445-2232", addresses.get(2), "Author of childrens books."));
			add(new Author("Sarah", "Connor", "123-422-2663", addresses.get(3), "Known for her clever style."));
		}
	};
	@SuppressWarnings("serial")
	static  List<Book> allBooks = new ArrayList<Book>() {
		{
			add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
			add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
			add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
			add(new Book("48-56882", "Jimmy s First Day of School", 7, Arrays.asList(allAuthors.get(4))));
			 // next book's maxCheckoutLength is intentionally made -7 to trigger past due date
            add(new Book("48-56888", "Trigger past due date book", -7, Arrays.asList(allAuthors.get(4))));    
		}
	};

	static List<User> allUsers = new ArrayList<User>() {
		{
			add(new User("101", "xyz", Auth.LIBRARIAN));
			add(new User("102", "abc", Auth.ADMIN));
			add(new User("103", "111", Auth.BOTH));
		}
	};

	public static void main(String[] args) {

		BookService bookService = new BookService();

		bookService.save(allBooks);

		bookService.addCopies(allBooks.get(0).getIsbn(),2);
		bookService.addCopies(allBooks.get(1).getIsbn(),1);
		bookService.addCopies(allBooks.get(3).getIsbn(),1);
		bookService.addCopies(allBooks.get(2).getIsbn(),4);
		bookService.addCopies(allBooks.get(4).getIsbn(),2);

		bookService.save(allBooks);

		AuthorService authorService=new AuthorService();
		authorService.save(allAuthors);


		addMembers();

		MemberService memberService =  new MemberService();
		memberService.save(members);

		UserService userService =  new UserService();
		userService.save(allUsers);


	}

	public static void addMembers(){
		Member Member = new Member("1001", "Andy", "Rogers", "641-223-2211", addresses.get(4));
		members.add(Member);

		Member = new Member("1002", "Drew", "Stevens", "702-998-2414", addresses.get(5));
		members.add(Member);

		Member = new Member("1003", "Sarah", "Eagleton", "451-234-8811", addresses.get(6));
		members.add(Member);

		Member = new Member("1004", "Ricardo", "Montalbahn", "641-472-2871", addresses.get(7));
		members.add(Member);

		Member = new Member("1005", "Sandra", "Thomas", "641-445-2123", addresses.get(0));          // author who happen to be a library member
		members.add(Member);
	}


	public static void load() {
		main(null);
	}
}
