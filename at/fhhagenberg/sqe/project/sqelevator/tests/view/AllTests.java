package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ 
	ElevatorStatusTest.class, 
	MoveStatusTest.class
})

public class AllTests {
}
