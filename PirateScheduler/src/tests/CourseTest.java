package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import Course.Activity;
import Course.Course;

/**
 * Tests the Course class.
 * 
 * @author Alicia Abrams
 */
public class CourseTest {

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

	/**
	 * Reads in file with course name for greater changeability of tests
	 */
	@Before
	public void readInCourseNameFile() {
		File file = new File("test-files/CourseName.txt");
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
		} catch (FileNotFoundException e) {
			System.out.println("Did not find file");
			fail();
		}

	}

	/**
	 * Tests the Course constructor.
	 */
	@Test
	public void testCourseConstructor() {
		Course c = null;
		try {
			// name = null
			c = new Course(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			c = null;
			// name = empty string
			c = new Course("", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			c = null;
			// name <4 characters
			c = new Course("S55", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			c = null;
			// name > 5 characters
			c = new Course("CSC3333", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			fail();

		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		// Test a valid construction
		c = null;
		try {
			c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests the Course constructor with Arranged meeting day because it does not
	 * include meeting times.
	 */
	@Test
	public void testCourseConstructorA() {
		// Test a valid construction of a course
		Course c = null;
		try {
			c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "A");
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals("A", c.getMeetingDays());
			assertEquals(0, c.getStartTime());
			assertEquals(0, c.getEndTime());
		} catch (IllegalArgumentException e) {
			fail();
		}
		// Tests construction of a Course with A and W. This is to test if it treats the
		// course like A or MTWHF
		try {
			c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "AW");
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals("A", c.getMeetingDays());
			assertEquals(0, c.getStartTime());
			assertEquals(0, c.getEndTime());
		} catch (IllegalArgumentException e) {
			// If the code reaches this point, the code caught that if it was A there should
			// only be one character
			assertEquals("A", "A");
		}
		// Tests construction of a Course with W and A . This is to test if it treats
		// the course like A or MTWHF
		try {
			c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "WA", START_TIME, END_TIME);
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals("WA", c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		} catch (IllegalArgumentException e) {
			// If the code reaches this point, the code caught that if it was A there should
			// only be one character
			assertEquals("A", "A");
		}
		// Test extra day
		c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "MTWTFF", START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests setTitle().
	 */
	@Test
	public void testSetTitle() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the title to null or empty string doesn't change the title
		try {
			c.setTitle(null);
			c.setTitle("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Valid set
		c.setTitle("Art of Painting");
		assertEquals(NAME, c.getName());
		assertEquals("Art of Painting", c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests setSection().
	 */
	@Test
	public void testSetSection() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the section to null, empty string, ><3 characters, doesn't
		// change the section
		try {
			c.setSection(null);
			c.setSection("");
			c.setSection("11");
			c.setSection("0012");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Test valid section
		c.setSection("002");
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals("002", c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests that the credits are set correctly.
	 */
	@Test
	public void testSetCredits() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the credits to 0 or 6 doesn't change the credits
		try {
			c.setCredits(0);
			c.setCredits(6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Test valid credits
		c.setCredits(3);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(3, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests that the instructor id is set correctly.
	 */
	@Test
	public void testSetInstructorId() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the instructor id to null or empty string doesn't change
		// the instructor id
		try {
			c.setInstructorId(null);
			c.setInstructorId("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Valid set
		c.setInstructorId("jtking");
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals("jtking", c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests that the meeting days are set correctly.
	 */
	@Test
	public void testSetMeetingDays() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the meeting days to null, empty string or invalid
		// characters doesn't change the meeting days
		try {
			c.setMeetingDays(null);
			c.setMeetingDays("");
			c.setMeetingDays("MWS");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Valid set
		c.setMeetingDays("TH");
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals("TH", c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());
	}

	/**
	 * Tests that the course times are set correctly.
	 */
	@Test
	public void testSetCourseTime() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(START_TIME, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Test that setting the start time to 2400, -1 doesn't change the start
		// time
		try {
			c.setActivityTime(2400, 1445);
			c.setActivityTime(-1, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}
		
		// set start time to lower boundary
		try {
			c.setActivityTime(0000, 1445);
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(0000, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// set end time to lower boundary
		c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		try {
			c.setActivityTime(1330, 0000);
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// Test that setting the end time to 2400, -1 doesn't change the end time
		try {
			c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
			c.setActivityTime(1330, 2400);
			c.setActivityTime(1330, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Test that having the start time after the end time doesn't change the values.
		try {
			c.setActivityTime(1445, 1330);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(NAME, c.getName());
			assertEquals(TITLE, c.getTitle());
			assertEquals(SECTION, c.getSection());
			assertEquals(CREDITS, c.getCredits());
			assertEquals(INSTRUCTOR_ID, c.getInstructorId());
			assertEquals(MEETING_DAYS, c.getMeetingDays());
			assertEquals(START_TIME, c.getStartTime());
			assertEquals(END_TIME, c.getEndTime());
		}

		// Valid set of start time
		c.setActivityTime(1350, 2359);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(1350, c.getStartTime());
		assertEquals(END_TIME, c.getEndTime());

		// Valid set of end time
		c.setActivityTime(2359, 0100);
		assertEquals(NAME, c.getName());
		assertEquals(TITLE, c.getTitle());
		assertEquals(SECTION, c.getSection());
		assertEquals(CREDITS, c.getCredits());
		assertEquals(INSTRUCTOR_ID, c.getInstructorId());
		assertEquals(MEETING_DAYS, c.getMeetingDays());
		assertEquals(1350, c.getStartTime());
		assertEquals(1526, c.getEndTime());
	}

	/**
	 * Tests that getMeetingString() works correctly
	 */
	@Test
	public void testGetMeetingString() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals("MW 1:30PM-2:45PM", c1.getMeetingString());
		Activity c3 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "A");
		assertEquals("Arranged", c3.getMeetingString());
	}

	/**
	 * Tests that the equals method works for all Course fields.
	 */
	@Test
	public void testEqualsObject() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME,
				END_TIME);
		Activity c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", MEETING_DAYS, START_TIME, END_TIME);
		Activity c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "TH", START_TIME, END_TIME);
		Activity c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, 830, END_TIME);
		Activity c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, 1400);

		// Test for equality in both directions
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));

		// Test for each of the fields
		assertFalse(c1.equals(c3));
		assertFalse(c1.equals(c4));
		assertFalse(c1.equals(c5));
		assertFalse(c1.equals(c6));
		assertFalse(c1.equals(c7));
		assertFalse(c1.equals(c8));
		assertFalse(c1.equals(c9));
	}

	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME,
				END_TIME);
		Activity c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", MEETING_DAYS, START_TIME, END_TIME);
		Activity c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "TH", START_TIME, END_TIME);
		Activity c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, 830, END_TIME);
		Activity c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, 1400);

		// Test for the same hash code for the same values
		assertEquals(c1.hashCode(), c2.hashCode());

		// Test for each of the fields
		assertNotEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
		assertNotEquals(c1.hashCode(), c5.hashCode());
		assertNotEquals(c1.hashCode(), c6.hashCode());
		assertNotEquals(c1.hashCode(), c7.hashCode());
		assertNotEquals(c1.hashCode(), c8.hashCode());
		assertNotEquals(c1.hashCode(), c9.hashCode());
	}

	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	public void testToString() {
		Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME);
		String s1 = "CSC333,Introduction to software testing,004,3,abramsa20,MW,1330,1445";
		assertEquals(s1, c1.toString());

		Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, "A");
		String s2 = "CSC333,Introduction to software testing,004,3,abramsa20,A";
		assertEquals(s2, c2.toString());
	}
}