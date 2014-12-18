/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** ModelTests
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ ElevatorTest.class,
				ElevatorSystemTest.class,
				ElevatorExceptionTest.class })
public class ModelTests {

}
