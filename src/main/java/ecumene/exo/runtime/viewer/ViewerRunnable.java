package ecumene.exo.runtime.viewer;

import java.beans.ExceptionListener;

import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.sim.ISimStepListener;

public abstract class ViewerRunnable implements Runnable, ISimContextListener, ISimStepListener {

	private String[] input;
	private int id;
	private ExceptionListener listener;
	
	public ViewerRunnable(int id, ExceptionListener exceptionListener) {
		this.id = id;
		this.listener = exceptionListener;
	}
	
	public int getID(){
		return id;
	}
	
	@Override
	public void run() {
		try {
			init();
		} catch (Throwable e) {
			listener.exceptionThrown((Exception) e);
		}
	}
	
	public abstract void init() throws Throwable;
	public abstract void kill(int id);
	
}
