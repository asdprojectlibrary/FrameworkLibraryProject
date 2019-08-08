//package business;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CheckoutRecord implements Serializable{
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = -2275062362742960250L;
//	private List<CheckoutEntry> checkoutEntries;
//	private LibraryMember owner;
//
//	public List<CheckoutEntry> getCheckoutEntries() {
//		return checkoutEntries;
//	}
//
//	CheckoutRecord(LibraryMember owner) {
//		this.owner = owner;
//		this.checkoutEntries = new ArrayList<CheckoutEntry>();
//	}
//
//	public LibraryMember getOwner() {
//		return owner;
//	}
//
//    void addCheckoutEntry(CheckoutEntry entry) {
//		checkoutEntries.add(entry);
//	}
//
//	@Override
//	public String toString() {
//		return "CheckoutRecord [checkoutEntries=" + checkoutEntries +"]";
//	}
//
//
//
//
//}
