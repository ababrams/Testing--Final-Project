package io;
/**
 * 
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import Course.Course;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Alicia Clery
 *
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		ArrayList<Course> courses = new ArrayList<Course>();
		Course course = null;
		while (fileReader.hasNextLine()) {
			try {
				course = readCourse(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}
			} catch (IllegalArgumentException e) {
				// skip
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Reads in each line and separates it into variables that are separated by a
	 * comma.
	 * 
	 * @throws IllegalArgumentException if file not found
	 * @param nextLine String
	 * @return course Course
	 */
	private static Course readCourse(String nextLine) {
		Scanner input = new Scanner(nextLine);
		Course course = null;

		try {
			input.useDelimiter(",");
			String name = input.next();
			String title = input.next();
			String section = input.next();
			int credits = input.nextInt();
			String instructorId = input.next();
			String meetingDays = input.next();
			if (!meetingDays.equals("A")) {
				int startTime = input.nextInt();
				int endTime = input.nextInt();
				course = new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
			} else {
				if (input.hasNext()) {
					input.close();
					throw new IllegalArgumentException();
				}
				course = new Course(name, title, section, credits, instructorId, meetingDays);
			}
		} catch (IllegalArgumentException e) {
			input.close();
			throw e;
		} catch (Exception e) {
			input.close();
			throw new IllegalArgumentException();
		}
		input.close();
		return course;

	}
}
