package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import Course.Activity;
import Course.ConflictException;
import Course.Course;
import scheduler.PirateScheduler;

/**
 * Tests the PirateScheduler class.
 * 
 * @author Alica Abrams
 */
public class PirateSchedulerTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";

	/** Course name */
	private static String NAME;
	/** Course title */
	private static String TITLE;
	/** Course section */
	private static String SECTION;
	/** Course credits */
	private static int CREDITS;
	/** Course instructor id */
	private static String INSTRUCTOR_ID;
	/** Course meeting days */
	private static String MEETING_DAYS;
	/** Course start time */
	private static int START_TIME;
	/** Course end time */
	private static int END_TIME;
	/** Event title */
	private static String EVENT_TITLE;
	/** Event meeting days */
	private static String EVENT_MEETING_DAYS;
	/** Event start time */
	private static int EVENT_START_TIME;
	/** Event end time */
	private static int EVENT_END_TIME;
	/** Event repeat */
	private static int EVENT_WEEKLY_REPEAT;
	/** Event details */
	private static String EVENT_DETAILS;

	/**
	 * Reads in file with course name for greater changeability of tests
	 */
	@Before
	public void readInNameFile() {
		File file = new File("test-files/SchedulerName");
		Scanner input;
		try {
			input = new Scanner(file);
			input.useDelimiter(",");
			NAME = input.next();
			TITLE = input.next();
			SECTION = input.next();
			CREDITS = input.nextInt();
			INSTRUCTOR_ID = input.next();
			MEETING_DAYS = input.next();
			START_TIME = input.nextInt();
			END_TIME = input.nextInt();
			EVENT_TITLE = input.next();
			EVENT_MEETING_DAYS = input.next();
			EVENT_START_TIME = input.nextInt();
			EVENT_END_TIME = input.nextInt();
			EVENT_WEEKLY_REPEAT = input.nextInt();
			EVENT_DETAILS = input.next();
		} catch (FileNotFoundException e) {
			System.out.println("Did not find file");
			fail();
		}
	}

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if it is unable to reset the file
	 */
	@Before
	public void setUp() throws Exception {
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests PirateScheduler().
	 */
	@Test
	public void testPirateScheduler() {
		// Test with invalid file. Should have an empty catalog and schedule.
		PirateScheduler ws1 = new PirateScheduler(invalidTestFile);
		assertEquals(0, ws1.getCourseCatalog().length); 
		assertEquals(0, ws1.getScheduledActivities().length);
		assertEquals(0, ws1.getFullScheduledActivities().length);
		assertEquals("My Schedule", ws1.getTitle());
		ws1.exportSchedule("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");

		// Repeated issue of not reading in all files in IO test FAIL
		PirateScheduler ws2 = new PirateScheduler(validTestFile);
		assertEquals(9, ws2.getCourseCatalog().length);
	}

	/**
	 * Test PirateScheduler.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Get a course that doesn't exist
		assertNull(ws.getCourseFromCatalog("CSC492", "001"));

		// Get a course that does exist
		Activity c = new Course("CSC604", "Introduction to software testing", "001", 3, "abramsa", "MW", 910, 1100);
		assertEquals(c, ws.getCourseFromCatalog("CSC604", "001"));
	}

	/**
	 * Test PirateScheduler.addCourse().
	 */
	@Test
	public void testAddCourse() throws ConflictException {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Attempt to add a course that doesn't exist
		assertFalse(ws.addCourse("CSC492", "001"));
		assertEquals(0, ws.getScheduledActivities().length);
		assertEquals(0, ws.getFullScheduledActivities().length);

		Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);

		// Attempt to add a course that does exist
		// assertTrue(

		ws.addCourse(NAME, SECTION);
		assertEquals(1, ws.getScheduledActivities().length);
		assertEquals(1, ws.getFullScheduledActivities().length);
		String[] course = ws.getFullScheduledActivities()[0];
		assertEquals(NAME, course[0]);
		assertEquals(SECTION, course[1]);
		assertEquals(TITLE, course[2]);
		assertEquals("" + CREDITS, course[3]);
		assertEquals(INSTRUCTOR_ID, course[4]);
		assertEquals(c.getMeetingString(), course[5]);
		assertEquals("", course[6]);

		// Attempt to add a course that already exists, even if different section
		try {
			ws.addCourse(NAME, "002");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in " + NAME, e.getMessage());
		}
		// Attempt to add a conflicting course
		try {
			ws.addCourse("CSC50", "007");
			ws.addCourse("CSC645", "001");
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}

	}

	/**
	 * Test PirateScheduler.addEvent().
	 */
	@Test
	public void testAddEvent() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		ws.addEvent(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT,
				EVENT_DETAILS);

		assertEquals(1, ws.getScheduledActivities().length);
		assertEquals(1, ws.getFullScheduledActivities().length);
		String[] course = ws.getFullScheduledActivities()[0];
		assertEquals("", course[0]);
		assertEquals("", course[1]);
		assertEquals(EVENT_TITLE, course[2]);
		assertEquals("", course[3]);
		assertEquals("", course[4]);
		assertEquals("MWF 7:30AM-8:30AM (every 3 weeks)", course[5]);
		assertEquals(EVENT_DETAILS, course[6]);

		// Attempt to add an event with the same title
		try {
			ws.addEvent(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT,
					EVENT_DETAILS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You have already created an event called Running", e.getMessage());
		}
		// add event with conflicting time
		try {
			ws.addEvent("make", EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT, "pizza");
		} catch (IllegalArgumentException e) {
			assertEquals("The event cannot be added due to a conflict.", e.getMessage());
		}
	}

	/**
	 * Test PirateScheduler.removeCourse().
	 */
	@Test
	public void testRemoveActivity() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Attempt to remove from empty schedule
		assertFalse(ws.removeActivity(0));

		// Add some courses and remove them
		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC333", "006"));

		ws.addEvent(EVENT_TITLE, EVENT_MEETING_DAYS, EVENT_START_TIME, EVENT_END_TIME, EVENT_WEEKLY_REPEAT,
				EVENT_DETAILS);
		assertTrue(ws.addCourse("CSC650", "001"));
		assertEquals(4, ws.getScheduledActivities().length);
		assertEquals(4, ws.getFullScheduledActivities().length);

		// Check that removing a course that doesn't exist when there are
		// scheduled courses doesn't break anything
		assertFalse(ws.removeActivity(5));
		assertEquals(4, ws.getScheduledActivities().length);
		assertEquals(4, ws.getFullScheduledActivities().length);

		// Remove Exercise
		assertTrue(ws.removeActivity(1));
		assertEquals(3, ws.getScheduledActivities().length);
		assertEquals(3, ws.getFullScheduledActivities().length);

		// Remove CSC604
		assertTrue(ws.removeActivity(1));
		assertEquals(2, ws.getScheduledActivities().length);
		assertEquals(2, ws.getFullScheduledActivities().length);

		// Remove CSC333
		assertTrue(ws.removeActivity(1));
		assertEquals(1, ws.getScheduledActivities().length);
		assertEquals(1, ws.getFullScheduledActivities().length);

		// Remove CSC650
		assertTrue(ws.removeActivity(0));
		assertEquals(0, ws.getScheduledActivities().length);
		assertEquals(0, ws.getFullScheduledActivities().length);

		// Check that removing all doesn't break future adds

		assertTrue(ws.addCourse("CSC650", "001"));

		assertEquals(1, ws.getScheduledActivities().length);
		assertEquals(1, ws.getFullScheduledActivities().length);
	}

	/**
	 * Test PirateScheduler.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Add some courses and reset schedule

		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC333", "007"));
		assertTrue(ws.addCourse("CSC650", "001"));

		assertEquals(3, ws.getScheduledActivities().length);
		assertEquals(3, ws.getFullScheduledActivities().length);

		ws.resetSchedule();
		assertEquals(0, ws.getScheduledActivities().length);
		assertEquals(0, ws.getFullScheduledActivities().length);

		// Check that resetting doesn't break future adds
		assertTrue(ws.addCourse(NAME, SECTION));
		assertEquals(1, ws.getScheduledActivities().length);
		assertEquals(1, ws.getFullScheduledActivities().length);
	}

	/**
	 * Test PirateScheduler.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Get the catalog and make sure contents are correct
		// Name, section, title
		String[][] catalog = ws.getCourseCatalog();
		// Row 0
		assertEquals("CSC604", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Introduction to software testing", catalog[0][2]);
		assertEquals("MW 9:10AM-11:00AM", catalog[0][3]);
		// Row 1
		assertEquals("CSC604", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Introduction to software testing", catalog[1][2]);
		assertEquals("MW 11:20AM-1:10PM", catalog[1][3]);
		// Row 2
		assertEquals("CSC604", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Introduction to software testing", catalog[2][2]);
		assertEquals("TH 11:20AM-1:10PM", catalog[2][3]);
		// Row 3
		assertEquals("CSC604", catalog[3][0]);
		assertEquals("004", catalog[3][1]);
		assertEquals("Introduction to software testing", catalog[3][2]);
		assertEquals("TH 9:10AM-11:00AM", catalog[3][3]);
		// Row 4
		assertEquals("CSC604", catalog[4][0]);
		assertEquals("005", catalog[4][1]);
		assertEquals("Introduction to software testing", catalog[4][2]);
		assertEquals("TH 1:30PM-2:45PM", catalog[4][3]);
		// Row 5
		assertEquals("CSC333", catalog[5][0]);
		assertEquals("006", catalog[5][1]);
		assertEquals("UX Design", catalog[5][2]);
		assertEquals("MW 1:30PM-2:45PM", catalog[5][3]);
		// Row 6
		assertEquals("CSC333", catalog[6][0]);
		assertEquals("007", catalog[6][1]);
		assertEquals("UX Design", catalog[6][2]);
		assertEquals("Arranged", catalog[6][3]);
		// Row 7
		assertEquals("CSC645", catalog[7][0]);
		assertEquals("001", catalog[7][1]);
		assertEquals("Discrete Mathematics", catalog[7][2]);
		assertEquals("MWF 9:35AM-10:25AM", catalog[7][3]);
		// Row 8
		assertEquals("CSC650", catalog[8][0]);
		assertEquals("001", catalog[8][1]);
		assertEquals("Art History", catalog[8][2]);
		assertEquals("MH 3:00PM-4:00PM", catalog[8][3]);
	}

	/**
	 * Test PirateScheduler.getScheduledCourses().
	 */
	@Test
	public void testGetScheduledActivities() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Add some courses and get schedule
		// Name, section, title
		try {
			assertTrue(ws.addCourse(NAME, SECTION));
			assertTrue(ws.addCourse("CSC650", "001")); // FAIL no conflict
			ws.addEvent("Lunch", "MWF", 1500, 1600, 1, "Food");
		} catch (Exception e) {
			fail();
		}
		String[][] schedule = ws.getScheduledActivities();
		// Row 1
		assertEquals("CSC604", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Introduction to software testing", schedule[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule[0][3]);
		// Row 1
		assertEquals("CSC650", schedule[1][0]);
		assertEquals("001", schedule[1][1]);
		assertEquals("Art History", schedule[1][2]);
		assertEquals("MH 3:00PM-4:00PM", schedule[1][3]);
		// Row 2
		assertEquals("", schedule[2][0]);
		assertEquals("", schedule[2][1]);
		assertEquals("Lunch", schedule[2][2]);
		assertEquals("MWF 3:00PM-4:00PM (every 1 weeks)", schedule[2][3]);
	}

	/**
	 * Test PirateScheduler.getFullScheduledCourses()
	 */
	@Test
	public void testGetFullScheduledActivities() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Add some courses and get full schedule
		// Name, section, title, credits, instructor id, meeting string

		assertTrue(ws.addCourse(NAME, SECTION));
		assertTrue(ws.addCourse("CSC333", "007"));

		ws.addEvent("Lunch", "MWF", 1200, 1300, 1, "Food");

		String[][] schedule = ws.getFullScheduledActivities();
		// Row 1
		assertEquals("CSC604", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Introduction to software testing", schedule[0][2]);
		assertEquals("3", schedule[0][3]);
		assertEquals("abramsa", schedule[0][4]);
		assertEquals("MW 9:10AM-11:00AM", schedule[0][5]);
		// Row 1
		assertEquals("CSC333", schedule[1][0]);
		assertEquals("007", schedule[1][1]);
		assertEquals("UX Design", schedule[1][2]);
		assertEquals("4", schedule[1][3]);
		assertEquals("kubiak", schedule[1][4]);
		assertEquals("Arranged", schedule[1][5]);
		// Row 2
		assertEquals("", schedule[2][0]);
		assertEquals("", schedule[2][1]);
		assertEquals("Lunch", schedule[2][2]);
		assertEquals("", schedule[2][3]);
		assertEquals("", schedule[2][4]);
		assertEquals("MWF 12:00PM-1:00PM (every 1 weeks)", schedule[2][5]);
		assertEquals("Food", schedule[2][6]);
	}

	/**
	 * Test PirateScheduler.setTitle().
	 */
	@Test
	public void testSetTitle() {
		PirateScheduler ws = new PirateScheduler(validTestFile);

		// Set Title and check that changed
		ws.setTitle("New Title");
		assertEquals("New Title", ws.getTitle());

		// Check that exception is thrown if null title and no
		// change to title already there.
		try {
			ws.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("New Title", ws.getTitle());
		}
	}

	/**
	 * Test PirateScheduler.exportSchedule().
	 */
	@Test
	public void testExportSchedule() {
		// Test that empty schedule exports correctly
		PirateScheduler ws = new PirateScheduler(validTestFile);
		ws.exportSchedule("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");

		// Add courses and test that exports correctly
		ws.addCourse("CSC604", "001");
		ws.addCourse("CSC333", "007");

		assertEquals(2, ws.getScheduledActivities().length);
		ws.exportSchedule("test-files/actual_schedule_export.txt");
		checkFiles("test-files/expected_schedule_export.txt", "test-files/actual_schedule_export.txt");
		//FAIL due to not reading in 0910, it read in 910
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
				Scanner actScanner = new Scanner(new File(actFile));) {

			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}