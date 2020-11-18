package Course;

/**
 * Object Class activity for PirateScheduler
 * 
 * @author Alicia Clery
 *
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Highest value for military time */
	private final int upperTime = 2400;
	/** Highest value for minutes */
	private final int upperHour = 60;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructor for the activity super class
	 * 
	 * @param title       String
	 * @param meetingDays String
	 * @param startTime   int
	 * @param endTime     int
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Returns the Course's title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title.
	 * 
	 * @throws IllegalArgumentException if title is null
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Invalid title");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's meeting days.
	 * 
	 * @return the Meetingdays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Course's Meeting Days.
	 * 
	 * @throws IllegalArgumentException if meetingDays is not the correct char or is
	 *                                  null or empty.
	 * @param meetingDays the meetingDays to set t
	 */
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.meetingDays = meetingDays;
	}

	/**
	 * Sets the Course's StartTime. Sets the Course's EndTIme.
	 * 
	 * @throws IllegalArgumentException if startTime or endTime is out of bounds.
	 * @param endTime   the endTime to set
	 * @param startTime the StartTime to set
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (this.meetingDays.charAt(0) == 'A' && startTime != 0 && endTime != 0) {
			throw new IllegalArgumentException();
		}
		if (startTime < 0000 || startTime > 2359) {
			throw new IllegalArgumentException();
		}
		if (startTime > 0000) {
			String start = Integer.toString(startTime);
			int stime = Integer.parseInt(start.substring(start.length() - 2));
			if (stime > 59) {
				throw new IllegalArgumentException();
			}
		}
		if (endTime < 0000 || endTime > 2359) {
			throw new IllegalArgumentException();
		}
		if (endTime > 0000) {
			String end = Integer.toString(endTime);
			int etime = Integer.parseInt(end.substring(end.length() - 2));
			if (etime > 59) {
				throw new IllegalArgumentException();
			}
		}
		if (endTime < startTime) {
			throw new IllegalArgumentException();
		}
		this.endTime = endTime;
		this.startTime = startTime;
	}

	/**
	 * Returns the Activity's start time.
	 * 
	 * @return the start time
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's end time.
	 * 
	 * @return the end time
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Converts the military time to 12 hour time. If meetingDays is "A", time is
	 * not inputed
	 * 
	 * @return String meetingDays and time
	 */
	public String getMeetingString() {
		if (meetingDays.equals("A")) {
			return "Arranged";
		}
		String start = Integer.toString(startTime);
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
		String end = Integer.toString(endTime);
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

		return meetingDays + " " + s + sampm + "-" + e + eampm;

	}

	/**
	 * generates the hashcode for the activity object class
	 * 
	 * @return result the has code for the activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + upperHour;
		result = prime * result + upperTime;
		return result;
	}

	/**
	 * compares this activity with inputed activity and checks for equality return
	 * boolean false if they are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (upperHour != other.upperHour)
			return false;
		if (upperTime != other.upperTime)
			return false;
		return true;
	}

	/**
	 * checks if time is not conflicting
	 * 
	 * @param a Activity object that is entered by the user
	 * @throws ConflictException if the activitys have conflicting times on the same
	 *                           day
	 */
	public void checkConflict(Activity a) throws ConflictException {
		String b = a.getMeetingDays();
		String c = this.getMeetingDays();
		if (b.charAt(0) == 'A') {
			return;
		}
		for (int i = 0; i < b.length(); i++) {
			for (int q = 0; q < c.length(); q++) {
				if (b.charAt(i) == c.charAt(q) && !this.isValidTime(a)) {
					throw new ConflictException();
				}
			}
		}
	}

	/**
	 * Determines if the time conflicts with an activity already on the schedule
	 * 
	 * @param a Activity object to be added to the schedule
	 * @return boolean true if the time is valid
	 */
	public boolean isValidTime(Activity a) {
		if (this.getStartTime() == a.getEndTime() || a.getStartTime() == this.getEndTime()) {
			return false;
		}
		if (this.getStartTime() < a.getEndTime() && this.getStartTime() > a.getStartTime()) {
			return false;
		}
		if (a.getStartTime() < this.getEndTime() && a.getStartTime() > this.getStartTime()) {
			return false;
		}
		if (a.getStartTime() == this.getStartTime() || a.getEndTime() == this.getEndTime()) {
			return false;
		}
		return true;
	}

	/**
	 * creates an array of object course
	 * 
	 * @return shortDisplay array
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * creates an array of object course
	 * 
	 * @return longDisplay array
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Determines if the activity is a duplicate of an existing activity
	 * 
	 * @param activity Activity
	 * @return boolean boolean
	 */
	public abstract boolean isDuplicate(Activity activity);
}