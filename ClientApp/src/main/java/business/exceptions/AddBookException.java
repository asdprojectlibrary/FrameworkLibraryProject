package business.exceptions;

import java.io.Serializable;

public class AddBookException extends Exception implements Serializable {

	public AddBookException() {
		super();
	}
	public AddBookException(String msg) {
		super(msg);
	}
	public AddBookException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 4522558878411418L;
	
}
