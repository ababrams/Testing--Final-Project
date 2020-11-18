
package Course;

/**
 * Event Object Class for PirateScheduler
 * 
 * @author Alicia Abrams
 *
 */
public class Event extends Activity {
	/** Course's weekly repeat */
	private int weeklyRepeat;
	/** Details about the event */
	private String eventDetails;

	/**
	 * Constructor for event object class
	 * 
	 * @param title        String
	 * @param meetingDays  String
	 * @param startTime    int
	 * @param endTime      int
	 * @param weeklyRepeat int
	 * @param eventDetails String
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, int weeklyRepeat, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setWeeklyRepeat(weeklyRepeat);
		setEventDetails(eventDetails);
	}

	/**
	 * Sets the event's Meeting Days.
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
			throw new IllegalArgumentException();
		} else {
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M' || meetingDays.charAt(i) == 'T' || meetingDays.charAt(i) == 'W'
						|| meetingDays.charAt(i) == 'H' || meetingDays.charAt(i) == 'F' || meetingDays.charAt(i) == 'U'
						|| meetingDays.charAt(i) == 'S') {
					continue;
				} else {
					throw new IllegalArgumentException();
				}
			}
		}

		super.setMeetingDays(meetingDays);
	}

	/**
	 * sets weeklyRepeat
	 * 
	 * @param weeklyRepeat int
	 * @throws IllegalArgumentException if the weeklyRepeat is invalid
	 */
	public void setWeeklyRepeat(int weeklyRepeat) {
		if (weeklyRepeat < 1 || weeklyRepeat > 4) {
			throw new IllegalArgumentException("Invalid weekly repeat");
		}
		this.weeklyRepeat = weeklyRepeat;
	}

	/**
	 * gets the weeklyRepeat int
	 * 
	 * @return weeklyRepeat int
	 */
	public int getWeeklyRepeat() {
		return weeklyRepeat;
	}

	/**
	 * Sets the event details
	 * 
	 * @param eventDetails String
	 * @throws IllegalArgumentException if eventDetails is invalid
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details");
		}
		this.eventDetails = eventDetails;
	}

	/**
	 * gets eventDetails
	 * 
	 * @return eventDetails String
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Converts the military time to 12 hour time. If meetingDays is "A", time is
	 * not inputed
	 * 
	 * @return String meetingDays and time
	 */
	public String getMeetingString() {
		if (super.getMeetingDays().equals("A")) {
			return "Arranged";
		}
		String start = Integer.toString(getStartTime());
		int stime = Integer.parseInt(start.substring(0, start.length() - 2));
		String sampm = "AM";
		if (stime == 12) {
			sampm = "PM";
		}
		if (stime > 12) {
			sampm = "PM";
			stime -= 12;
		}
		String s = stime + ":" + start.substring(start.length() - 2);
		String end = Integer.toString(getEndTime());
		int etime = Integer.parseInt(end.substring(0, end.length() - 2));
		String eampm = "AM";
		if (etime == 12) {
			eampm = "PM";
		}
		if (etime > 12) {
			eampm = "PM";
			etime -= 12;
		}
		String e = etime + ":" + end.substring(end.length() - 2);

		return getMeetingDays() + " " + s + sampm + "-" + e + eampm + " (every " + weeklyRepeat + " weeks)";

	}

	/**
	 * returns an array of event objects
	 * 
	 * @return shortDisplay array[]
	 */
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		shortDisplay[0] = "";
		shortDisplay[1] = "";
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		return shortDisplay;
	}

	/**
	 * returns an array of event objects
	 * 
	 * @return longDisplay array[]
	 */
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = "";
		longDisplay[1] = "";
		longDisplay[2] = getTitle();
		longDisplay[3] = "";
		longDisplay[4] = "";
		longDisplay[5] = getMeetingString();
		longDisplay[6] = eventDetails;
		return longDisplay;
	}

	/**
	 * generates hashCode
	 * 
	 * @return result int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + getEndTime();
		result = prime * result + ((getMeetingDays() == null) ? 0 : getMeetingDays().hashCode());
		result = prime * result + getStartTime();
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
		return result;
	}

	/**
	 * generates determines if the objects are equal
	 * 
	 * @return boolean boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (getEndTime() != other.getEndTime())
			return false;
		if (getMeetingDays() == null) {
			if (other.getMeetingDays() != null)
				return false;
		} else if (!getMeetingDays().equals(other.getMeetingDays()))
			return false;
		if (getStartTime() != other.getStartTime())
			return false;
		if (!getTitle().equals(other.getTitle()))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	public String toString() {
		String line = getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + ","
				+ weeklyRepeat + "," + eventDetails;
		return line;
	}

	/**
	 * Determines if the Event is a duplicate of an existing Event
	 * 
	 * @param activity Activity
	 * @return boolean boolean
	 */
	public boolean isDuplicate(Activity activity) {
		Event e;
		if (activity instanceof Event) {
			e = (Event) activity;
		} else {
			return false;
		}

		if (this.getEventDetails().equals(e.getEventDetails()) && this.getMeetingDays().equals(e.getMeetingDays())) {
			return true;
		}
		return false;
	}
}
