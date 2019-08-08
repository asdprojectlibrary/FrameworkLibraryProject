package ui;

import java.time.LocalDate;

public class OverdueView {
	private String isbn;
	private String title;
	private int copyNumber;
	private String member;
	private LocalDate dueDate;
	public OverdueView(String isbn, String title, int copyNumber, String member, LocalDate dueDate) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.copyNumber = copyNumber;
		this.member = member;
		this.dueDate = dueDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getTitle() {
		return title;
	}
	public int getCopyNumber() {
		return copyNumber;
	}
	public String getMember() {
		return member;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	
}
