package ecumene.exo.sim.solar;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.planet.GenericSolarObject;

public class ExoSolarMap extends RObject {
	private RMap solarSystem;
	private List<IExoSolarObject> objects;
	private long seed;
	private OpenSimplexNoise noise;
	
	public ExoSolarMap(long seed) { 
		objects = new ArrayList<IExoSolarObject>();
		objects.add(new GenericSolarObject(new Vector2f(1f, 1f), new Vector2f(1, 1)));
	}
	
	public RMap step(){
		RPoint[] point = new RPoint[objects.size()];
		
		for(int i = 0; i < objects.size(); i++) {
			point[i] = objects.get(i).step(objects);
		}
		
		solarSystem = new RMap(point);
		return solarSystem;
	}
	
	@Override
	public String getName(int id) {
		return "E0 " + id;
	}
}
