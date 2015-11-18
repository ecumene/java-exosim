package ecumene.exo.sim.solar;

import java.beans.ExceptionListener;

import ecumene.exo.IExoRunnableTag;

public class ExoSolarMapTag implements IExoRunnableTag {

	private long seed;
	
	public ExoSolarMapTag(long seed){
		this.seed = seed;
	}
	
	public long getSeed(){
		return seed;
	}
	
	@Override
	public String getIdentifier(){
		return "Solar simulation";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new ExoSolarMapRenderer(id, listener, new ExoSolarMap(id), args);
	}
}
