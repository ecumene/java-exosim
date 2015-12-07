package ecumene.exo.sim;

import ecumene.exo.ExoRuntime;

public class ESContext {
	private long  seed;
	private float interp = 1f;
	private int   indexSolar; // Selected Solar System (solarSeed = seed + indexSolar)
	private int   solarSteps; // The steps for simulating the solar system (also represents global/local steps)
	private int   solarFollow;// Index of the object to follow in 
	
	public ESContext(long seed, int solar) {
		this.seed = seed;
		this.indexSolar = solar;
		solarSteps = 0;
	}
	
	public void setSolarFollow(int solarFollow){
		this.solarFollow = solarFollow;
	}
	
	public int getSolarFollow(){
		return solarFollow;
	}
	
	public void step(){
		this.solarSteps += 1;
		ExoRuntime.INSTANCE.step(interp);
	}
	
	public void setStepInterp(float set){
		interp = set;
	}
	
	public float getStepInterp(){
		return interp;
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
