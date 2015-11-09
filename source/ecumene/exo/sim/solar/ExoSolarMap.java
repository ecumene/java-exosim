package ecumene.exo.sim.solar;

import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;

public class ExoSolarMap extends RObject {
	private RMap solarSystem;
	
	public ExoSolarMap() {
		// setup solar system
	}
	
	public RMap getSolarSystem(){
		return solarSystem;
	}
}
