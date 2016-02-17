package ecumene.exo.sim;

import ecumene.exo.sim.planet.ExoPlanetMap;

public class SimPlanetContext {
	
	private SimSolarContext solarSystem;
	private ExoPlanetMap map;
	
	public SimPlanetContext(SimSolarContext context, ExoPlanetMap map) {
		this.solarSystem = context;
		this.map      = map;
	}
	
	public SimSolarContext getSolarSystem() {
		return solarSystem;
	}
	
	public ExoPlanetMap getMap() {
		return map;
	}
}
