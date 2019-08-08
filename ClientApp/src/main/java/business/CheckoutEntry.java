//package business;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//
//public class CheckoutEntry implements Serializable {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = -6322883311177243175L;
//	private LocalDate checkoutDate;
//	private LocalDate dueDate;
//	private BookCopy bookCopy;
//	private CheckoutRecord checkoutRecord;
//
//	public CheckoutEntry( BookCopy bookCopy,CheckoutRecord checkoutRecord) {
//		this.checkoutDate =  LocalDate.now();
//		this.dueDate = checkoutDate.plusDays(bookCopy.getBook().getMaxCheckoutLength());
//		this.bookCopy = bookCopy;
//		this.checkoutRecord = checkoutRecord;
//
//	}
//	public LocalDate getCheckoutDate() {
//		return checkoutDate;
//	}
//	public void setCheckoutDate(LocalDate checkoutDate) {
//		this.checkoutDate = checkoutDate;
//	}
//	public LocalDate getDueDate() {
//		return dueDate;
//	}
//	public void setDueDate(LocalDate dueDate) {
//		this.dueDate = dueDate;
//	}
//	public BookCopy getBookCopy() {
//		return bookCopy;
//	}
//
//	public CheckoutRecord getCheckoutRecord() {
//		return checkoutRecord;
//	}
//	@Override
//	public String toString() {
//		return "\nCheckoutEntry [checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", book=" + bookCopy.getBook().getIsbn() + " copy " + bookCopy.getCopyNum()+ "]";
//	}
//
//
//
//}
