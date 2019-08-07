package model;

import lombok.Data;
import lombok.NoArgsConstructor;

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

}
