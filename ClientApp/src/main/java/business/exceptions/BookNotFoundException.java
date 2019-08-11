package business.exceptions;

import java.io.Serializable;

public class BookNotFoundException extends Exception implements Serializable {

	public BookNotFoundException() {
		super();
	}
	public BookNotFoundException(String msg) {
		super(msg);
	}
	public BookNotFoundException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 2343345456567657L;
	
}
