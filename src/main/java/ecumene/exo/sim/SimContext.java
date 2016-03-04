package ecumene.exo.sim;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.abstractions.SimGalaxyContext;
import ecumene.exo.sim.abstractions.SimPlanetContext;
import ecumene.exo.sim.abstractions.SimSolarContext;
import ecumene.exo.sim.abstractions.SimSurfaceContext;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;

public class SimContext {
	private int steps  = 0;
	public boolean running;
	
	// By default they're all null. They're initialized when they're
	// selected in their respected simulations... Genius, I know. ;)
	private SimGalaxyContext galaxy = null;
	private SimSolarContext solar  = null;
	private SimPlanetContext planet = null;
	private SimSurfaceContext surface = null;

	public SimContext(ExoGalaxyMap galaxyMap) {
		this(galaxyMap, null, null, null);
	}
	
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap) {
		this(galaxyMap, solarMap, null, null);
	}

	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap, ExoPlanetMap planetMap){ this(galaxyMap, solarMap, planetMap, null); }

	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap, ExoPlanetMap planetMap, ExoSurfaceMap surfaceMap) {
		galaxy  = new SimGalaxyContext (this, galaxyMap);
		solar   = new SimSolarContext  (galaxy, solarMap);
		planet  = new SimPlanetContext (solar, planetMap);
		surface = new SimSurfaceContext(planet, surfaceMap);
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

	public SimSurfaceContext getSurface() {
		return surface;
	}

	public void step(){
		ExoRuntime.INSTANCE.step();
		steps++;
	}

	public int getSteps() {
		return steps;
	}
}
