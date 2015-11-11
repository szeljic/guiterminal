package szeljic.exception;

public class ArgumentsMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArgumentsMatchException() {
	}

	public ArgumentsMatchException(String message) {
		super(message);
	}

	public ArgumentsMatchException(Throwable cause) {
		super(cause);
	}

	public ArgumentsMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentsMatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
