/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.fhhagenberg.sqe.project.sqelevator.communication.CommunicationTests;
import at.fhhagenberg.sqe.project.sqelevator.model.ModelTests;
import at.fhhagenberg.sqe.project.sqelevator.view.ViewTests;

/** AllTests
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ModelTests.class,
			   ViewTests.class,
			   CommunicationTests.class})
public class AllTests {

}
