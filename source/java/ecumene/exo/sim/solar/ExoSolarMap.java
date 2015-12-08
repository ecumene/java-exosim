package ecumene.exo.sim.solar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.orbit.ESDOrbit;

public class ExoSolarMap extends RObject {
	private RMap solarSystem;
	private List<IExoSolarObject> objects;
	private long seed;
	private OpenSimplexNoise noise;
	public static float G = 6.67f; // Really 6.67x10^-11
	
	public ExoSolarMap(long seed) { 
		noise = new OpenSimplexNoise(seed);
		objects = new ArrayList<IExoSolarObject>();
		
		int size = (int) (noise.eval(0, 1) * 100) + 1; // Solar size = noise * max (max=10^1)
		size = Math.abs(size);
		
		GenericSolarObject earth = new GenericSolarObject(new Vector2f(0, 0), new Vector2f(0, 0));
		earth.mass = 0.1f;
		
		GenericSolarObject moon  = new GenericSolarObject(new Vector2f(0, ESDOrbit.getDistance(1f, 0.5f)), new Vector2f());
		moon.mass = 0.1f * 0.25f;
		moon.addDisplacement(new ESDOrbit("deltaV", new Color(0, 255, 0), moon, earth, (float)(0.3f)));
		
		GenericSolarObject moon2  = new GenericSolarObject(new Vector2f(0, -3f), new Vector2f());
		moon2.mass = 0.1f * 0.15f;
		moon2.addDisplacement(new ESDOrbit("deltaV", new Color(0, 255, 0), moon2, earth, (float)(0.3f)));

		objects.add(earth); // Sun
		objects.add(moon); // Sun
		objects.add(moon2); // Sun
		
//		for(int i = 0; i < size; i++){
//			objects.add(new GenericSolarObject(new Vector2f((float)noise.eval(0, (i * 4) + 1),
//					                                        (float)noise.eval(0, (i * 4) + 2)), new Vector2f(0, 0)));
//		}
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
