package ecumene.exo;

import java.beans.ExceptionListener;
import java.util.concurrent.Callable;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;

import ecumene.exo.sim.IESContextListener;
import ecumene.exo.sim.IESStepListener;

public abstract class ExoRunnable implements Runnable, IESContextListener, IESStepListener {

	private String[] input;
	private int id;
	private ExceptionListener listener;
	
	public ExoRunnable(int id, ExceptionListener exceptionListener, String...args) {
		this.input = args;
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
