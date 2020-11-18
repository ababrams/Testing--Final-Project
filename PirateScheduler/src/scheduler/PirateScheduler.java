package scheduler;

import java.io.IOException;
import java.util.ArrayList;

import Course.Activity;
import Course.ConflictException;
import Course.Course;
import Course.Event;
import io.ActivityRecordIO;
import io.CourseRecordIO;
/**
 * Creates the schedule using the Course class.
 * 
 * @author Alicia Clery
 *
 */
public class PirateScheduler {
	/** Title of the schedule */
	private String title;
	/** array that represents the courses in the catalog */
	ArrayList<Course> courseCatalog;
	/** array that represents the courses added to the schedule */
	ArrayList<Activity> schedule;

	/**
	 * Creates a schedule using Arraylists of Activity objects pulled in from a
	 * file.
	 * 
	 * @throws IllegalArgumentException when file not found
	 * @param filename String
	 */
	public PirateScheduler(String filename) {
		title = "My Schedule";
		schedule = new ArrayList<Activity>();
		try {
			courseCatalog = CourseRecordIO.readCourseRecords(filename);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * Gets Course from courseCatalog ArrayList. If course does not exist it returns
	 * null
	 * 
	 * @param name    String
	 * @param section String
	 * @return course object
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (Course c : courseCatalog) {
			if (c.getName().contains(name) && c.getSection().contains(section)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Adds the course to the schedule if the course exists or is not a duplicate.
	 * 
	 * @throws IllegalArgumentException if the course is a duplicate
	 * @param name    String
	 * @param section String
	 * @return boolean
	 */
	public boolean addCourse(String name, String section) {
		Course cc = getCourseFromCatalog(name, section);
		if (cc == null) {
			return false;
		}
		for (Activity c : schedule) {
			if (cc.isDuplicate(c)) {
				throw new IllegalArgumentException("You are already enrolled in " + cc.getName());
			}
			if (!cc.isValidTime(c)) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(cc);
		return true;
	}

	/**
	 * adds event
	 * 
	 * @param title String
	 * @param meetingDays String
	 * @param startTime int
	 * @param endTime int
	 * @param weeklyRepeat int
	 * @param eventDetails String
	 */
	public void addEvent(String title, String meetingDays, int startTime, int endTime, int weeklyRepeat,
			String eventDetails) {
		Event e1 = new Event(title, meetingDays, startTime, endTime, weeklyRepeat, eventDetails);
		for (Activity a : schedule) {
			if (a instanceof Event) {
				if (e1.getTitle().equals(a.getTitle())) {
					throw new IllegalArgumentException("You have already created an event called " + title);
				}
				try {
					e1.checkConflict(a);
				} catch (ConflictException e) {
					throw new IllegalArgumentException("The event cannot be added due to a conflict.");
				}
				if (e1.isDuplicate(a)) {
					throw new IllegalArgumentException("You are already enrolled in " + a.getTitle());
				}
				if (e1.getMeetingString() == null) {
					throw new IllegalArgumentException("The event must occur on at least one day");
				}
			}
		}
		schedule.add(e1);
	}

	/**
	 * Removes Course object from the schedule ArrayList.
	 * 
	 * @param idx int
	 * 
	 * @return boolean
	 */
	public boolean removeActivity(int idx) {
		if (schedule.size() == 0 || idx > schedule.size()) {
			return false;
		}
		Activity act = schedule.get(idx);
		for (Activity a : schedule) {
			if (a.equals(act)) {
				schedule.remove(a);
				return true;
			}
		}
		return false;
	}

	/**
	 * Replaces schedule ArrayList with new ArrayList
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Activity>();
	}

	/**
	 * Creates an 2D object array that inputs the Course as the row and three
	 * columns for name, section, and title.
	 * 
	 * @return catalog String [][]
	 */
	public String[][] getCourseCatalog() {
		String[][] catalog = new String[courseCatalog.size()][4];
		for (int i = 0; i < courseCatalog.size(); i++) {
			catalog[i][0] = courseCatalog.get(i).getName();
			catalog[i][1] = courseCatalog.get(i).getSection();
			catalog[i][2] = courseCatalog.get(i).getTitle();
			catalog[i][3] = courseCatalog.get(i).getMeetingString();
		}
		return catalog;
	}

	/**
	 * Creates an 2D object array that inputs the Course as the row and three
	 * columns for name, section, and title.
	 * 
	 * @return sCourses String [][]
	 */
	public String[][] getScheduledActivities() {
		String[][] sCourses = new String[schedule.size()][4];
		int i = 0;
		for (Activity a : schedule) {
			if (a instanceof Course) {
				Course c = (Course) a;
				sCourses[i][0] = c.getName();
				sCourses[i][1] = c.getSection();
				sCourses[i][2] = c.getTitle();
				sCourses[i][3] = c.getMeetingString();
			} else if (a instanceof Event) {
				Event e = (Event) a;
				sCourses[i][0] = "";
				sCourses[i][1] = "";
				sCourses[i][2] = e.getTitle();
				sCourses[i][3] = e.getMeetingString();
			}
			i++;
		}
		return sCourses;
	}

	/**
	 * Creates a 2D object array where there is a row for each Course and six
	 * columns for name, section, title, credits, instructorId, and the meetingDays
	 * String. If there are no Courses in the schedule, an empty String array is
	 * returned.
	 * 
	 * @return sCourses String[][]
	 */
	public String[][] getFullScheduledActivities() {
		String[][] sCourses = new String[schedule.size()][7];
		int i = 0;
		for (Activity a : schedule) {
			if (a instanceof Course) {
				Course c = (Course) a;
				sCourses[i][0] = c.getName();
				sCourses[i][1] = c.getSection();
				sCourses[i][2] = c.getTitle();
				sCourses[i][3] = c.getCredits() + "";
				sCourses[i][4] = c.getInstructorId();
				sCourses[i][5] = c.getMeetingString();
				sCourses[i][6] = "";
			}
			if (a instanceof Event) {
				Event b = (Event) a;
				sCourses[i][0] = "";
				sCourses[i][1] = "";
				sCourses[i][2] = b.getTitle();
				sCourses[i][3] = "";
				sCourses[i][4] = "";
				sCourses[i][5] = b.getMeetingString();
				sCourses[i][6] = b.getEventDetails();
			}
			i++;
		}
		return sCourses;
	}

	/**
	 * Sets this.title to title if title is not null
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("The title cannot be null");
		}
		this.title = title;
	}

	/**
	 * gets title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * exports the schedule
	 * 
	 * @throws IllegalArgumentException if the file cannot be saved
	 * @param filename String
	 */
	public void exportSchedule(String filename) {
		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved");
		}

	}
}
