package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Course.Activity;
import Course.ConflictException;
import Course.Course;
import Course.Event;

/**
 * Tests the interface Conflict
 * 
 * @author Alicia Abrams
 *
 */
public class ConflictTest {
	/**
	 * Tests if there is no conflict commutatively
	 */
	@Test
	public void noConflictCommutativeTest() {
		Activity a = new Event("Dog Walking", "MT", 900, 1000, 2, "Walk Tofu");
		Activity b = new Event("Dog Walking", "WF", 900, 1000, 2, "Walk Tofu");
		try {
			a.checkConflict(b);
			b.checkConflict(a);
		} catch (ConflictException e) {
			fail();
		}
	}

	/**
	 * Tests if it only overlaps on one day
	 */
	@Test
	public void overlapTestOneDayTest() {
		Activity a = new Event("Dog Walking", "MT", 900, 1000, 2, "Walk Tofu");
		Activity b = new Event("Eating", "TF", 900, 1000, 2, "Walk Tofu");
		try {
			a.checkConflict(b);
		} catch (ConflictException e) {
			assertEquals(e.getMessage(), "Schedule conflict.");
		}
	}

	/**
	 * Tests if a course and event that are overlapping 
	 */
	@Test
	public void eventandCourseTest() {
		Activity a = new Course("Cook", "C100", "002", 4, "aclery", "TF", 900, 1000);
		Activity b = new Event("Dog Walking", "TF", 900, 1000, 2, "Walk Tofu");
		try {
			a.checkConflict(b);
		} catch (ConflictException e) {
			assertEquals(e.getMessage(), "Schedule conflict.");
		}
	}
	/**
	 * Tests if the method catches if the endtimes of the course and event overlap
	 */
	@Test
	public void overlappingEndTimeTest() {
		Activity a = new Course("Cook", "C100", "002", 4, "aclery", "TF", 900, 1000);
		Activity b = new Event("Dog Walking", "TF", 1000, 1030, 2, "Walk Tofu");
		try {
			a.checkConflict(b);
			fail();
		} catch (ConflictException e) {
			assertEquals(e.getMessage(), "Schedule conflict.");
		}
	}
}
