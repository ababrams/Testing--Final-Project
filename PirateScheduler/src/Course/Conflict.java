/**
 * 
 */
package Course;

/**
 * Interface for checking if a activity conflicts.
 * 
 * @author Alicia Clery
 *
 */
public interface Conflict {
	/**
	 * Checks if the Activity time overlaps with another Activity. It does this by
	 * calling the activity class and checking there.
	 * 
	 * @param possibleConflictingActivity Activity that is being added to the
	 *                                    schedule to see if it conflicts with an
	 *                                    activity already on the schedule
	 * @throws ConflictException custom exception that is thrown if the activity time
	 *                           and date conflicts with an activity already on the
	 *                           schedule
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
