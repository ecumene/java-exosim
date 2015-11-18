package ecumene.exo.sim;

import ecumene.exo.ExoRuntime;

public class ESContext {
	private long seed;
	private int  indexSolar; // Selected Solar System (solarSeed = seed + indexSolar)
	private int  solarSteps; // The steps for simulating the solar system (also represents global/local steps)
	
	public ESContext(long seed, int solar) {
		this.seed = seed;
		this.indexSolar = solar;
		solarSteps = 0;
	}
	
	public void step(){
		this.solarSteps += 1;
		ExoRuntime.INSTANCE.step();
	}
	
	public void setSteps(int steps){
		this.solarSteps = steps;
	}
	
	public int getSteps(){
		return solarSteps;
	}
	
	public long getSeed(){
		return seed;
	}
	
	public int getSolarIndex(){
		return indexSolar;
	}	
	
	public long getSolarSeed(){
		return seed + indexSolar;
	}	
	
}
