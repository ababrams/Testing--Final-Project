package io;


import java.io.File;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import Course.Activity;


/**
 * Writes the activity records to a file
 * @author Alicia Clery
 *
 */

public class ActivityRecordIO {

	/**
	 * Writes the given list of Courses to an ArrayList
	 * 
	 * @param fileName String
	 * @param courses  Course Object ArrayList
	 * @throws IOException checked exception
	 */
	public static void writeActivityRecords(String fileName, ArrayList<Activity> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (Activity c : courses) {
			fileWriter.println(c.toString());
		}

		fileWriter.close();
	}

}
