package tests;

import static org.junit.Assert.*;

import org.junit.Test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.Before;
import Course.Course;
import io.CourseRecordIO;
/**
 * Tests CourseRecordIO.
 * 
 * @author Alicia Abrams
 */
public class InputTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";

	/** Expected results for valid courses */
	private final String validCourse1 = "CSC604,Introduction to software testing,001,3,abramsa,MW,0910,1100";
	private final String validCourse2 = "CSC604,Introduction to software testing,002,azizi,MW,1120,1310";
	private final String validCourse3 = "CSC604,Introduction to software testing,003,3,smith,TH,1120,1310";
	private final String validCourse4 = "CSC604,Introduction to software testing,004,3,jackson,TH,0910,1100";
	private final String validCourse5 = "CSC604,Introduction to software testing,005,4,clery,TH,1330,1445";
	private final String validCourse6 = "CSC604,Introduction to software testing,006,4, marcelin,MW,1330,1445";
	private final String validCourse7 = "CSC604,Introduction to software testing,601,4, kubiak,A";
	private final String validCourse8 = "CSC645,Discrete Mathematics,001,3,clery,MWF,935,1025";
	private final String validCourse9 = "CSC650,Art History,001,3, marcelin,MW,1145,1300";
	/** Array to hold expected results */
	private final String[] validCourses = { validCourse1, validCourse2, validCourse3, validCourse4, validCourse5,
			validCourse6, validCourse7, validCourse8, validCourse9};

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception IOException
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
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
	 * Tests readValidCourseRecords().
	 */
	@Test
	public void testReadValidCourseRecords() {
		try {
			ArrayList<Course> courses = CourseRecordIO.readCourseRecords(validTestFile);
			assertEquals(9, courses.size());

			for (int i = 0; i < validCourses.length; i++) {
				//FAIL did not read in 0910, only read in 910
				assertEquals(validCourses[i], courses.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests readInvalidCourseRecords().
	 */
	@Test
	public void testReadInvalidCourseRecords() {
		ArrayList<Course> courses;
		try {
			courses = CourseRecordIO.readCourseRecords(invalidTestFile);
			assertEquals(0, courses.size());  
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests the default constructor
	 */
	@Test
	public void testDefaultConstructor() {
		CourseRecordIO c = new CourseRecordIO();
		assertTrue(c != null);
	}
}