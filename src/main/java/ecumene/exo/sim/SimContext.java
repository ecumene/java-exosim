package ecumene.exo.sim;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.planet.ESPlanetMap;
import ecumene.exo.sim.solar.ExoSolarMap;

public class SimContext {
	private int interp = 1;
	private int steps  = 0;
	public boolean running;
	
	// By default they're all null. They're initialized when they're
	// selected in their respected simulations... Genius, I know. ;)
	private SimGalaxyContext galaxy = null;
	private SimSolarContext  solar  = null;
	private SimPlanetContext planet = null;
	
	public SimContext(ExoGalaxyMap galaxyMap) {
		this(galaxyMap, null, null);
	}
	
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap) {
		this(galaxyMap, solarMap, null);
	}
	
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap, ESPlanetMap planetMap) {
		galaxy = new SimGalaxyContext (this, galaxyMap);
		solar  = new SimSolarContext  (galaxy, solarMap);
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
	
	public void setStepInterp(int set){
		interp = set;
	}
	
	public int getStepInterp(){
		return interp;
	}
	
	public int getSteps() {
		return steps;
	}
}
