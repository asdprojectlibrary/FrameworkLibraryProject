package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data

@NoArgsConstructor
final public class Book extends BaseModel {

    private List<BookCopy> copies;
    private List<Author> authors;
    private String isbn;
    private String title;
    private int maxCheckoutLength;

    public int getNumCopies(){
        return copies.size();
    }

    public void setIsbn(String isbn) {
        this.setIsbn(isbn);
        super.setId(isbn);
    }

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        this.isbn = isbn;
        this.setId(this.isbn);
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.authors = Collections.unmodifiableList(authors);
        copies = new ArrayList<>();
        copies.add(new BookCopy(this, 1, true));
    }
}
