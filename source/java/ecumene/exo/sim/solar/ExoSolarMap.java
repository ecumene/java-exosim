package ecumene.exo.sim.solar;

import java.util.ArrayList;
import java.util.List;

import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;

public class ExoSolarMap extends RObject {
	private RMap solarSystem;
	private List<IExoSolarObject> objects;
	private long seed;
	
	public ExoSolarMap(long seed) { 
		objects = new ArrayList<IExoSolarObject>();
	}
	
	public RMap getRenderable(){
		RPoint[] point = new RPoint[objects.size()];

		for(int i = 0; i < objects.size(); i++) {
			
		}
		
		solarSystem = new RMap(point);
		return solarSystem;
	}
	
	@Override
	public String getName(int id) {
		return "RSS " + id;
	}
}
