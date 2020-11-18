package Course;

/**
 * Class that creates a custom exception for the conflict interface in the
 * piratescheduler project
 * 
 * @author Alicia Clery
 *
 */
public class ConflictException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Parameterized constructor with a String specifying a message for the
	 * Exception object
	 * 
	 * @param message String
	 */
	public ConflictException(String message) {
		super(message);
		
	}

	/**
	 * Parameterless constructor that calls the parameterized constructor with an
	 * author specified default message
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
