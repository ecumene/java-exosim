package ecumene.exo.sim;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.planet.ESPlanetMap;
import ecumene.exo.sim.solar.ExoSolarMap;

public class SimContext {
	private float  interp = 0.001f;
	private int    steps  = 0;
	public boolean running;
	
	// By default they're all null. They're initialized when they're
	// selected in their respected simulations... Genius, I know. ;)
	private SimGalaxyContext galaxy = null;
	private SimSolarContext  solar  = null;
	private SimPlanetContext planet = null;
	
	public SimContext(ExoGalaxyMap galaxyMap) {
		this(galaxyMap, 0, null, null);
	}
	
	public SimContext(ExoGalaxyMap galaxyMap, int solarIndex, ExoSolarMap solarMap) {
		this(galaxyMap, solarIndex, solarMap, null);
	}
	
	public SimContext(ExoGalaxyMap galaxyMap, int solarIndex, ExoSolarMap solarMap, ESPlanetMap planetMap) {
		galaxy = new SimGalaxyContext (this, galaxyMap);
		solar  = new SimSolarContext  (galaxy, solarIndex, solarMap);
		planet = new SimPlanetContext (solar, planetMap);
	}
	
	public SimGalaxyContext getGalaxy(){
		return galaxy;
	}
	
	public SimSolarContext getSolarSystem(){
		return solar;
	}
	
	public SimPlanetContext getPlanet(){
		return planet;
	}
	
	public void step(){
		ExoRuntime.INSTANCE.step(interp);
		steps++;
	}
	
	public void setStepInterp(float set){
		interp = set;
	}
	
	public float getStepInterp(){
		return interp;
	}
	
	public int getSteps() {
		return steps;
	}
}
