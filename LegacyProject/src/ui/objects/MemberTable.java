package ui.objects;

public class MemberTable {
	private String memberId;
	private String firstName;
	private String lastName;
	private String telephone;	
	private String street;
	private String city;
	private String state;
	private String zip;
	
	public MemberTable(String m_memberId,String f, String l, String t, String m_street,String m_city,String m_state,String m_zip) {
		firstName = f;
		lastName = l;
		telephone = t;
		memberId=m_memberId;
		street=m_street;
		city=m_city;
		state=m_state;
		zip=m_zip;
		
	}
	public String getMemberId() {
		return memberId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getStreet() {
		return street;
	}
	
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
}
