package szeljic.exception;

public class UnknownColumnName extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnknownColumnName() {
	}

	public UnknownColumnName(String message) {
		super(message);
	}

	public UnknownColumnName(Throwable cause) {
		super(cause);
	}

	public UnknownColumnName(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownColumnName(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
