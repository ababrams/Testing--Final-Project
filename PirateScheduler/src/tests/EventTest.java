package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import Course.Activity;
import Course.Event;

/**
 * Tests the Event class.
 * 
 * @author Alicia Abrams
 */
public class EventTest {

	/** Event title */
	private static String TITLE;
	/** Event meeting days */
	private static String MEETING_DAYS;
	/** Event start time */
	private static int START_TIME;
	/** Event end time */
	private static int END_TIME;
	/** Event repeat */
	private static int WEEKLY_REPEAT;
	/** Event details */
	private static String DETAILS;

	/**
	 * Reads in file with event information for greater changeability of tests
	 */
	@Before
	public void readInNameFile() {
		File file = new File("test-files/EventName.txt");
		Scanner input;
		try {
			input = new Scanner(file);
			input.useDelimiter(",");
			TITLE = input.next();
			MEETING_DAYS = input.next();
			START_TIME = input.nextInt();
			END_TIME = input.nextInt();
			WEEKLY_REPEAT = input.nextInt();
			DETAILS = input.next();
		} catch (FileNotFoundException e) {
			System.out.println("Did not find file");
			fail();
		}
	}

	/**
	 * Test Event.Event().
	 */
	@Test
	public void testEvent() {
		Event a = null;
		Event b = null;
		Event c = null;
		Event d = null;
		Event l = null;
		Event f = null;
		Event g = null;
		Event h = null;
		Event i = null;
		Event j = null;
		Event k = null;
		try {
			// null title
			a = new Event(null, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
			// empty string title
			b = new Event("", MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
			// null meeting days
			c = new Event(TITLE, null, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
			// empty string meeting days
			d = new Event(TITLE, "", START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
			// invalid start time
			l = new Event(TITLE, MEETING_DAYS, 2400, END_TIME, WEEKLY_REPEAT, DETAILS);
			f = new Event(TITLE, MEETING_DAYS, -1, END_TIME, WEEKLY_REPEAT, DETAILS);
			// invalid end time
			g = new Event(TITLE, MEETING_DAYS, START_TIME, 2400, WEEKLY_REPEAT, DETAILS);
			h = new Event(TITLE, MEETING_DAYS, START_TIME, -1, WEEKLY_REPEAT, DETAILS);
			// invalid weekly repeat
			i = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, 0, DETAILS);
			j = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, 5, DETAILS);
			//invalid details
			k = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(a);
			assertNull(b);
			assertNull(c);
			assertNull(d);
			assertNull(l);
			assertNull(f);
			assertNull(g);
			assertNull(h);
			assertNull(i);
			assertNull(j);
			assertNull(k);
		}

		// Test a valid event
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());

	}

	/**
	 * Test Event.setMeetingDays()
	 */
	@Test
	public void testSetMeetingDays() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		//Tests that the meetingDays do not accept A - pass
		try {
			event.setMeetingDays("A");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		//Test set valid values 
		event.setMeetingDays("MTWH");
		assertEquals(TITLE, event.getTitle());
		assertEquals("MTWH", event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
		
		//Test extra day - Should not have changed it to double Friday Values - FAIL
		event.setMeetingDays("MTWHFF");
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());

		//Test invalid value - Changed to invalid value should only accept MTWHF - FAIL
		event.setMeetingDays("UMTWF");
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
	}

	/**
	 * Test Event.getMeetingString().
	 */
	@Test
	public void testGetMeetingString() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals("MWF 7:30AM-8:30AM (every 3 weeks)", event.getMeetingString());
	}

	/**
	 * Test Event.getShortDisplayArray().
	 */
	@Test
	public void testGetShortDisplayArray() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
		String[] actualShortDisplay = event.getShortDisplayArray();
		assertEquals("", actualShortDisplay[0]);
		assertEquals("", actualShortDisplay[1]);
		assertEquals(TITLE, actualShortDisplay[2]);
		assertEquals("MWF 7:30AM-8:30AM (every 3 weeks)", actualShortDisplay[3]);
	}

	/**
	 * Test Event.getLongDisplayArray().
	 */
	@Test
	public void testGetLongDisplayArray() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
		String[] actualLongDisplay = event.getLongDisplayArray();
		assertEquals("", actualLongDisplay[0]);
		assertEquals("", actualLongDisplay[1]);
		assertEquals(TITLE, actualLongDisplay[2]);
		assertEquals("", actualLongDisplay[3]);
		assertEquals("", actualLongDisplay[4]);
		assertEquals("MWF 7:30AM-8:30AM (every 3 weeks)", actualLongDisplay[5]);
		assertEquals(DETAILS, actualLongDisplay[6]);
	}

	/**
	 * Test Event.setWeeklyRepeat().
	 */
	@Test
	public void testSetWeeklyRepeat() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		try {
			event.setWeeklyRepeat(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		try {
			event.setWeeklyRepeat(5);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		event.setWeeklyRepeat(2);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(2, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
	}

	/**
	 * Test.Event.setEventDetails().
	 */
	@Test
	public void testSetEventDetails() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		try {
			event.setEventDetails(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		event.setEventDetails("Other details");
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals("Other details", event.getEventDetails());
	}

	/**
	 * Test Event.setTitle().
	 */
	@Test
	public void testSetTitle() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());

		// Test that setting the title to null doesn't change the title (or anything
		// else).
		try {
			event.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the title to "" doesn't change the title (or anything
		// else).
		try {
			event.setTitle("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Valid set
		event.setTitle("A new title");
		assertEquals("A new title", event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
	}

	/**
	 * Test Event.setCourseTime().
	 */
	@Test
	public void testSetCourseTime() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(START_TIME, event.getStartTime());
		assertEquals(END_TIME, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());

		// Test that setting the start time to 2400 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(2400, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the start time to 1360 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(0000, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the start time to -1 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(-1, 1445);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the end time to 2400 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(1445, 2400);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the start time to 1360 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(1445, 0000);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that setting the start time to -1 doesn't change the start time (or
		// anything else).
		try {
			event.setActivityTime(1330, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Test that having the start time after the end time doesn't change the values.
		try {
			event.setActivityTime(1445, 1330);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TITLE, event.getTitle());
			assertEquals(MEETING_DAYS, event.getMeetingDays());
			assertEquals(START_TIME, event.getStartTime());
			assertEquals(END_TIME, event.getEndTime());
			assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
			assertEquals(DETAILS, event.getEventDetails());
		}

		// Valid set of start time
		event.setActivityTime(1350, 1445);
		assertEquals(TITLE, event.getTitle());
		assertEquals(MEETING_DAYS, event.getMeetingDays());
		assertEquals(1350, event.getStartTime());
		assertEquals(1445, event.getEndTime());
		assertEquals(WEEKLY_REPEAT, event.getWeeklyRepeat());
		assertEquals(DETAILS, event.getEventDetails());
	}

	/**
	 * Test Event.toString().
	 */
	@Test
	public void testToString() {
		Event event = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		assertEquals(
				TITLE + "," + MEETING_DAYS + "," + START_TIME + "," + END_TIME + "," + WEEKLY_REPEAT + "," + DETAILS,
				event.toString());
	}

	/**
	 * Test Event.equals().
	 */
	@Test
	public void testEqualsObject() {
		Activity e1 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e2 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e3 = new Event("Title", MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e4 = new Event(TITLE, "MWF", START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e5 = new Event(TITLE, MEETING_DAYS, 830, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e6 = new Event(TITLE, MEETING_DAYS, START_TIME, 930, WEEKLY_REPEAT, DETAILS);
		Activity e7 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, 3, DETAILS);
		Activity e8 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, "Details");

		// Test for equality in both directions
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));

		// Test for each of the fields
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(e4)); 
		assertFalse(e1.equals(e5));
		assertFalse(e1.equals(e6));
		assertTrue(e1.equals(e7));
		assertTrue(e1.equals(e8));
	}

	/**
	 * Test Event.hashCode().
	 */
	@Test
	public void testHashCode() {
		Activity e1 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e2 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e3 = new Event("Title", MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e4 = new Event(TITLE, "MWF", START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e5 = new Event(TITLE, MEETING_DAYS, 830, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e6 = new Event(TITLE, MEETING_DAYS, START_TIME, 930, WEEKLY_REPEAT, DETAILS);
		Activity e7 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, 2, DETAILS);
		Activity e8 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, "Details");

		// Test for the same hash code for the same values
		assertEquals(e1.hashCode(), e2.hashCode());

		// Test for each of the fields
		assertNotEquals(e1.hashCode(), e3.hashCode());
		assertNotEquals(e1.hashCode(), e4.hashCode());  //FAIL, should be not equal but was
		assertNotEquals(e1.hashCode(), e5.hashCode());
		assertNotEquals(e1.hashCode(), e6.hashCode());
		assertEquals(e1.hashCode(), e7.hashCode()); 
		assertEquals(e1.hashCode(), e8.hashCode()); 

	}

	/**
	 * Test isDuplicate().
	 */
	@Test
	public void testIsDuplicate() {
		Activity e1 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e2 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		// Test duplicate events
		assertTrue(e1.isDuplicate(e2));

		Activity e3 = new Event(TITLE, MEETING_DAYS, START_TIME, END_TIME, WEEKLY_REPEAT, DETAILS);
		Activity e4 = new Event(TITLE, "MT", START_TIME, END_TIME, WEEKLY_REPEAT, "Eat lunch");
		// Test unique events
		assertFalse(e3.isDuplicate(e4));
	}
}
