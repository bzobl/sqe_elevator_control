package at.fhhagenberg.sqe.project.sqelevator.controller;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;

abstract public class ElevatorAlgorithm implements IElevatorAlgorithm {
	
	protected ElevatorSystem mModel;
	protected IElevatorControl mControl;
	protected boolean mEnabledElevators[];

	public ElevatorAlgorithm(ElevatorSystem sys, IElevatorControl ctrl) {
		mModel = sys;
		mControl = ctrl;
		mEnabledElevators = new boolean[mModel.NUM_ELEVATORS];
	}

	@Override
	public void setModel(ElevatorSystem model) {
		mModel = model;
	}

	@Override
	public void setControl(IElevatorControl control) {
		mControl = control;
	}
	
	@Override
	public void enableElevator(int elevator, boolean enable) {
		assert(elevator < mEnabledElevators.length);
		mEnabledElevators[elevator] = enable;
	}

}
