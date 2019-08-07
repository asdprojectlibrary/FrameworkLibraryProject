package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
final public class BookCopy extends BaseModel {
    private Book book;
    private long copyNum;
    private boolean isAvailable;

    public BookCopy(Book book, int copyNum, boolean isAvailable) {
        this.book = book;
        this.copyNum = copyNum;
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BookCopy))
            return false;

        BookCopy bookCopy = (BookCopy) o;

        return getId() == bookCopy.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
