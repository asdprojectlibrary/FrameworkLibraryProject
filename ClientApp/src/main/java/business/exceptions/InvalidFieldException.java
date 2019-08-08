package business.exceptions;

import java.io.Serializable;

public class InvalidFieldException extends Exception implements Serializable {

	public InvalidFieldException() {
		super();
	}
	public InvalidFieldException(String msg) {
		super(msg);
	}
	public InvalidFieldException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 485456544548165L;
	
}
