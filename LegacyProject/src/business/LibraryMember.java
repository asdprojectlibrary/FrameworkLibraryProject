package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private final String memberId;
	
	private final CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;		
		checkoutRecord = new CheckoutRecord(this);        // library member with zero checkout entries
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
	    return checkoutRecord;
	}
	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
