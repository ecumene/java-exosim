package ecumene.exo.impl;

import java.beans.ExceptionListener;

import ecumene.exo.ExoRunnable;
import ecumene.exo.sim.ESContext;

public class PlaceHolderRunnable extends ExoRunnable {

	private boolean running;
	private String[] args;
	
	public PlaceHolderRunnable(int id, ExceptionListener listener, String[] args) {
		super(id, listener, args);
		running = false;
		
	}
	
	@Override
	public void init() throws Throwable {
		running = true;
		while(running){}	
	}
	
	@Override
	public void kill(int id) {
		System.out.println("Example thread " + id + " is kill ;(");
		running = false;
	}

	@Override
	public void onContextChanged(ESContext context) {}
	
}
