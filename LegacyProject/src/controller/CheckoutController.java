package controller;

import business.CheckoutRecord;
import business.LibraryMember;

import java.util.List;

import business.Book;
import business.CheckoutEntry;
import dataaccess.dao.LibraryMemberRepository;
import dataaccess.exception.BookNotFound;
import dataaccess.dao.BookRepository;
import dataaccess.dao.CheckoutRecordRepository;
import exception.BookCopyNotAvailable;
import exception.LibraryMemberNotFound;

public class CheckoutController {
    
    public static Integer maxBook;
	
	public CheckoutRecord checkout(String memberId, String ISBN) 
	    throws LibraryMemberNotFound, BookNotFound, BookCopyNotAvailable {
	    LibraryMember member = LibraryMemberRepository.getMember(memberId);
	    Book book = BookRepository.getBook(ISBN);
	    CheckoutRecord checkoutRecord = book.checkout(member);
	    LibraryMemberRepository.updateMember(member);
	    CheckoutRecordRepository.updateRecord(checkoutRecord);
        BookRepository.updateBook(book);
	    return checkoutRecord;
	}
	
	public CheckoutRecord listCheckoutRecordForMember(String memberId) 
		    throws LibraryMemberNotFound {
		    LibraryMember member = LibraryMemberRepository.getMember(memberId);		    
		    CheckoutRecord checkoutRecord=member.getCheckoutRecord();
		    return checkoutRecord;
		}

	public List<CheckoutEntry> getOverdueBooks(String ISBN) throws BookNotFound {
	    return CheckoutRecordRepository.getOverdueCopies(BookRepository.getBook(ISBN).getIsbn());      // getBook required for validation
	}
	
	
}
