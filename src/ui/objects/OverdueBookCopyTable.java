package ui.objects;

import business.CheckoutEntry;

public class OverdueBookCopyTable {
	private final String memberFirstName;
	private final String memberLastName;
	private final String dueDate;
    private final String checkoutDate;
	private final String bookTitle;	
    private final String isbn;
    private final String copyId;
	
	public OverdueBookCopyTable(CheckoutEntry entry) {
		checkoutDate = entry.getCheckoutDateString();
		dueDate  = entry.getDueDateString();
		isbn = entry.getCheckoutItem().getBook().getIsbn();
        bookTitle = entry.getCheckoutItem().getBook().getTitle();
        copyId = entry.getCheckoutItem().getCopyNum().toString();
        memberFirstName = entry.getMember().getFirstName();
        memberLastName = entry.getMember().getLastName();
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

    public String getCopyId()
    {
        return copyId;
    }

    public String getMemberFirstName()
    {
        return memberFirstName;
    }

    public String getMemberLastName()
    {
        return memberLastName;
    }
	
}
