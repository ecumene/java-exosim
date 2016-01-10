package ecumene.exo.sim.solar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.runtime.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.orbit.ESDOrbit;

public class ExoSolarMap extends RPoint {
	private RMap solarSystem;
	private List<IExoSolarObject> objects;
	public static float G = 6.67f; // Really 6.67x10^-11
	
	public ExoSolarMap(Vector2f position) { 
		super("Solar System", position);
		objects = new ArrayList<IExoSolarObject>();
	}
	
	public List<IExoSolarObject> getObjects(){
		return objects;
	}
	
	public RMap step(float interp){
		RPoint[] points = new RPoint[objects.size()];
		
		for(int i = 0; i < objects.size(); i++)
			points[i] = objects.get(i).step(objects);
		
		solarSystem = new RMap(points);
		return solarSystem;
	}
	
	@Override
	public String getName(int id) {
		return "E0 " + id;
	}
}
