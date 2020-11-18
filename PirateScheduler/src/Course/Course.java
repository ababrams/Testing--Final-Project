package Course;

/**
 * Object Class that creates a Course with field's for name, title, section,
 * instructorId, meeting days, startTime and endTime.
 * 
 * @author Alicia Clery
 *
 */

public class Course extends Activity {
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Length of section */
	public final int sectionLength = 3;
	/** maximum length of the name */
	public final int maxNameLength = 6;
	/** Minimum length of the name */
	public final int minNameLength = 4;
	/** maximum credits */
	public final int maxCredits = 5;
	/** minimum credits */
	public final int minCredits = 1;

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * meetingDays, startTime, and endTime for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.
	 * 
	 * @throws @throws IllegalArgumentException if name is null
	 * @param name the name to set
	 */
	private void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (name.length() < minNameLength || name.length() > maxNameLength) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	/**
	 * Returns the Course's Section.
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section.
	 * 
	 * @throws IllegalArgumentException if section is null or empty
	 * @param section the section to set
	 */
	public void setSection(String section) {
		if (section == null || section.equals("00") || section.isEmpty() || section.length() != sectionLength) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < sectionLength; i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException();
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's Credits.
	 * 
	 * @return the name
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits.
	 * 
	 * @throws IllegalArgumentException if credit value is out of bounds
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < minCredits || credits > maxCredits) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorID.
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's InstructorID.
	 * 
	 * @throws IllegalArgumentException if instructorId is empty or null
	 * @param instructorId the instructorID to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		String line = name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
		if (getMeetingDays().equals("A")) {
			return line;
		} else {
			return line + "," + getStartTime() + "," + getEndTime();
		}
	}

	/**
	 * Generates hashCode for Course using all Fields.
	 * 
	 * @return hashCode for Course
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + maxCredits;
		result = prime * result + maxNameLength;
		result = prime * result + minCredits;
		result = prime * result + minNameLength;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + sectionLength;
		return result;
	}
	
	/**
	 * Compares a given object to this object for equality in all fields.
	 * 
	 * @param obj object to compare
	 * @return true if the objects are the same for all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (maxCredits != other.maxCredits)
			return false;
		if (maxNameLength != other.maxNameLength)
			return false;
		if (minCredits != other.minCredits)
			return false;
		if (minNameLength != other.minNameLength)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (sectionLength != other.sectionLength)
			return false;
		return true;
	}

	/**
	 * creates an array of object Course
	 * 
	 * @return shortDisplay array
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		shortDisplay[0] = name;
		shortDisplay[1] = section;
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		return shortDisplay;
	}

	/**
	 * creates an array of object course
	 * 
	 * @return longDisplay array
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = name;
		longDisplay[1] = section;
		longDisplay[2] = getTitle();
		longDisplay[3] = credits + "";
		longDisplay[4] = instructorId;
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}
	/**
	 * Determines if the Course is a duplicate of an existing course
	 * @param activity Activity object
	 * @return boolean boolean
	 */
	public boolean isDuplicate(Activity activity) {
		Course c;
		if (activity instanceof Course) {
			c = (Course) activity;
		} else {
			return false;
		}
		if (this.getName().equals(c.getName())) {
			return true;
		}
		return false;

	}
	/**
	 * Sets the Course's Meeting Days.
	 * 
	 * @throws IllegalArgumentException if meetingDays is not the correct char or is
	 *                                  null or empty.
	 * @param meetingDays the meetingDays to set 
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.isEmpty()) {
			throw new IllegalArgumentException();
		} else if (meetingDays.equals("A")) {
			super.setMeetingDays(meetingDays);
		} else {
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M' || meetingDays.charAt(i) == 'T' || meetingDays.charAt(i) == 'W'
						|| meetingDays.charAt(i) == 'H' || meetingDays.charAt(i) == 'F') {
					continue;
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		super.setMeetingDays(meetingDays);
}
}
