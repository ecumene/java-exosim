package ecumene.exo.sim.planet;

import java.util.List;

import ecumene.exo.runtime.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.solar.IExoSolarObject;

public class ESPlanetMap {
	
	private int solarIndex; // Index in the solar system map
	private RMap planetarySystem;
//	private List<IExoSolarObject> objects;
	public static float G = 6.67f; // Really 6.67x10^-11
	
	public ESPlanetMap() {
		
	}
}
