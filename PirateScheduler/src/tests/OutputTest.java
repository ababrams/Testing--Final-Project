package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Test;

import Course.Activity;
import Course.Course;
import Course.Event;
import io.ActivityRecordIO;

/**
 * Tests for ActivityRecordIO.
 * 
 * @author Alicia Abrams
 */
public class OutputTest {

	/**
	 * Tests writeActivityRecords()
	 */
	@Test
	public void testWriteActivityRecordswithCourses() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Course("CSC604", "Introduction to software testing", "001", 3, "abramsa", "MW", 910, 1100));
		activities.add(new Course("CSC604", "Introduction to software testing", "002", 4, "azizi", "MW", 1120, 1310));
		activities.add(new Course("CSC604", "Introduction to software testing", "003", 3, "smith", "A"));

		try {
			ActivityRecordIO.writeActivityRecords("test-files/actual_course_records.txt", activities);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}

		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");
	}

	/**
	 * Tests writeActivityRecords() with Event objects.
	 */
	@Test
	public void testWriteActivityRecordsWithEvents() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(new Course("CSC604", "Introduction to software testing", "001", 3, "abramsa", "MW", 910, 1100));
		activities.add(new Course("CSC604", "Introduction to software testing", "002", 4, "azizi", "MW", 1120, 1310));
		activities.add(new Event("Exercise", "UMTHFS", 800, 900, 1, "Cardio with rest day on Wednesday."));
		activities.add(new Course("CSC604", "Introduction to software testing", "003", 3, "smith", "A"));
		

		try {
			ActivityRecordIO.writeActivityRecords("test-files/actual_activity_records.txt", activities);
		} catch (IOException e) {
			fail("Cannot write to activity records file");
		}

		checkFiles("test-files/expected_activity_records.txt", "test-files/actual_activity_records.txt");
	}

	/**
	 * Tests writeActivityRecords() with Event objects invalid.
	 */
	@Test
	public void testWriteActivityRecordsWithEventsInvalid() {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		try {
			ActivityRecordIO.writeActivityRecords("test-files/actual_activity_records.txt", activities);
		} catch (IOException e) {
			fail("Cannot write to activity records file");
		} catch (NoSuchElementException e) {
			assertEquals("No line found", e.getMessage());
		}
	}

	/**
	 * Compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new File(expFile));
			Scanner actScanner = new Scanner(new File(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests the default constructor
	 */
	@Test
	public void testDefaultConstructor() {
		ActivityRecordIO c = new ActivityRecordIO();
		assertTrue(c != null);
	}
}
