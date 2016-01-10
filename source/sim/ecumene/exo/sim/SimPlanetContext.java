package ecumene.exo.sim;

import ecumene.exo.sim.planet.ESPlanetMap;

public class SimPlanetContext {
	
	private SimSolarContext solarSystem;
	private ESPlanetMap    planet;
	
	public SimPlanetContext(SimSolarContext context, ESPlanetMap map) {
		this.solarSystem = context;
		this.planet      = map;
	}
	
	public SimSolarContext getSolarSystem() {
		return solarSystem;
	}
	
	public ESPlanetMap getPlanet() {
		return planet;
	}
}
