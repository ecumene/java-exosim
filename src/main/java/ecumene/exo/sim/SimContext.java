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
import org.jdom2.Document;

/**
 * Represents all simulation objects
 */
public class SimContext {
	private int steps  = 0; // The steps in the simulation
	public boolean running; // Is the -simulation- running or stopped?
	
	// By default they're all null. They're initialized when they're
	// selected in their respected constructor... Genius, I know. ;)
	protected SimGalaxyContext galaxy = null;   // Context for all gen objects
	protected SimSolarContext solar  = null;    // Context for all solar objects
	protected SimPlanetContext planet = null;   // Context for all planet objects
	protected SimSurfaceContext surface = null; // Context for all surface objects

	protected SimContext() {}

	/**Clones a given simcontext
	 * @param clone The simcontext to clone
     */
	public SimContext(SimContext clone){
		this(clone.galaxy.getMap(), clone.solar.getSolarMap(), clone.planet.getMap(), clone.surface.getSurfaceMap());
	}

	/**
	 * Constructs a sim context
	 * @param galaxyMap The gen to simulate
     */
	public SimContext(ExoGalaxyMap galaxyMap) {
		this(galaxyMap, null, null, null);
	}

	/**
	 * Constructs a sim context
	 * @param galaxyMap The gen to simulate
	 * @param solarMap The solar system to simulate
     */
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap) {
		this(galaxyMap, solarMap, null, null);
	}

	/**
	 * Constructs a sim context
	 * @param galaxyMap The gen to simulate
	 * @param solarMap The solar system to simulate
	 * @param planetMap The planet to simulate
     */
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap, ExoPlanetMap planetMap){ this(galaxyMap, solarMap, planetMap, null); }

	/**
	 * Constructs a sim context
	 * @param galaxyMap The gen to simulate
	 * @param solarMap The solar system to simulate
	 * @param planetMap The planet to simulate
	 * @param surfaceMap The surface to simulate
     */
	public SimContext(ExoGalaxyMap galaxyMap, ExoSolarMap solarMap, ExoPlanetMap planetMap, ExoSurfaceMap surfaceMap) {
		galaxy  = new SimGalaxyContext (this, galaxyMap);
		solar   = new SimSolarContext  (galaxy, solarMap);
		planet  = new SimPlanetContext (solar, planetMap);
		surface = new SimSurfaceContext(planet, surfaceMap);
	}

	/** @return The simulation's gen */
	public SimGalaxyContext getGalaxy(){
		return galaxy;
	}

	/** @return The simulation's solar system*/
	public SimSolarContext getSolarSystem(){
		return solar;
	}

	/** @return The simulation's planet*/
	public SimPlanetContext getPlanet(){
		return planet;
	}

	/** @return The simulation's planet's surface*/
	public SimSurfaceContext getSurface() {
		return surface;
	}

	/**
	 * Step the simulation
	 */
	public void step(){
		ExoRuntime.INSTANCE.step();
		steps++;
	}

	/** @return The simulation's # of steps*/
	public int getSteps() {
		return steps;
	}
}
