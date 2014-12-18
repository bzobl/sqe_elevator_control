package at.fhhagenberg.sqe.project.sqelevator.controller;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

abstract public class ElevatorAlgorithm implements IElevatorAlgorithm {
	
	protected IElevatorSystem mModel;
	protected IElevatorControl mControl;
	protected boolean mEnabledElevators[];

	public ElevatorAlgorithm(IElevatorSystem sys, IElevatorControl ctrl) {
		mModel = sys;
		mControl = ctrl;
		mEnabledElevators = new boolean[mModel.getNumberOfElevators()];
	}

	@Override
	public void setModel(IElevatorSystem model) {
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
