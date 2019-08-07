package ui.objects;

import business.CheckoutEntry;

public class CheckoutRecordTable {
	private final String checkoutDate;
	private final String dueDate;
	private final String isbn;
	private final String bookTitle;	
    private final String copiesLeft;
    private final String authors;
    private final String copyId;
	
	public CheckoutRecordTable(CheckoutEntry entry) {
		checkoutDate = entry.getCheckoutDateString();
		dueDate  = entry.getDueDateString();
		isbn = entry.getCheckoutItem().getBook().getIsbn();
        bookTitle = entry.getCheckoutItem().getBook().getTitle();
        copiesLeft = entry.getCheckoutItem().getBook().getNumberOfAvailableCopies().toString();
        authors = entry.getCheckoutItem().getBook().getAuthors().toString();
        copyId = entry.getCheckoutItem().getCopyNum().toString();
	}

    public String getCheckoutDate()
    {
        return checkoutDate;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public String getBookTitle()
    {
        return bookTitle;
    }

    public String getCopiesLeft()
    {
        return copiesLeft;
    }

    public String getAuthors()
    {
        return authors;
    }

    public String getCopyId()
    {
        return copyId;
    }
	

}
