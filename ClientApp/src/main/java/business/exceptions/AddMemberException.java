package business.exceptions;

import java.io.Serializable;

public class AddMemberException extends Exception implements Serializable {

	public AddMemberException() {
		super();
	}
	public AddMemberException(String msg) {
		super(msg);
	}
	public AddMemberException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 324734839382343295L;
	
}
