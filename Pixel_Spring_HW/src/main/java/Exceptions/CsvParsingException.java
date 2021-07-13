package Exceptions;

public class CsvParsingException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CsvParsingException(String message, RuntimeException e) {
		super();
		this.message = message + " details: " + e.getMessage() + " " + e.getLocalizedMessage();
	}

	public String getMessage() {
		return message;
	}	

}
