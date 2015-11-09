package ecumene.exo.sim.galaxy;

import java.beans.ExceptionListener;

import ecumene.exo.IExoRunnableTag;

public class ExoGalaxyMapTag implements IExoRunnableTag {

	private long seed;
	
	public ExoGalaxyMapTag(long seed) {
		this.seed = seed;
	}
	
	public long getSeed(){
		return seed;
	}
	
	@Override
	public String getIdentifier() {
		return "Galaxy simulation";
	}

	@Override
	public Runnable construct(int id, ExceptionListener listener, String[] args) throws Throwable {
		return new ExoGalaxyMapRenderer(id, listener, new ExoGalaxyMap(seed), args);
	}
	
}
