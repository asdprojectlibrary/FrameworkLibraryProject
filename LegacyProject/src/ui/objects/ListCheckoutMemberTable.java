package ui.objects;

public class ListCheckoutMemberTable {
	
	private String member;
	private String bookAndISBN;
	private String checkOutDate;
	private String dueDate;
	private String bookCopyId;
	//private String telephone;
	
	public ListCheckoutMemberTable(String mem,String book,String cDate,String dDate,int idCopy) {
		member=mem;
		bookAndISBN=book;
		checkOutDate=cDate;
		dueDate=dDate;
		bookCopyId=""+idCopy;
	}
	
	public String getMember() {
		return member;
	}
	
	public String getBookCopyId() {
		return bookCopyId;
	}
	
	
	public String getBookAndISBN() {
		return bookAndISBN;
	}
	
	public String getCheckOutDate() {
		return checkOutDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	

}
