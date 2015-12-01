package ecumene.exo.sim.solar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.planet.GenericSolarObject;
import ecumene.exo.sim.solar.orbit.ESDOrbit;

public class ExoSolarMap extends RObject {
	private RMap solarSystem;
	private List<IExoSolarObject> objects;
	private long seed;
	private OpenSimplexNoise noise;
	
	public ExoSolarMap(long seed) { 
		noise = new OpenSimplexNoise(seed);
		objects = new ArrayList<IExoSolarObject>();
		
		int size = (int) (noise.eval(0, 1) * 100) + 1; // Solar size = noise * max (max=10^1)
		size = Math.abs(size);

		GenericSolarObject earth = new GenericSolarObject(new Vector2f(0, 0), new Vector2f(0, 0));
		earth.mass = 0.1f;
		
		GenericSolarObject moon  = new GenericSolarObject(new Vector2f(0, 1), new Vector2f());
		moon.mass = 0.1f * 0.25f;
		moon.addDisplacement(new ESDOrbit("deltaV", new Color(0, 255, 0), moon, earth, (float)(1.6675001E-13 * 1.6675001E-13)));

//		objects.add(earth); // Sun
//		objects.add(moon); // Sun		

		for(int i = 0; i < size; i++){
			objects.add(new GenericSolarObject(new Vector2f((float)noise.eval(0, (i * 4) + 1),
					                                        (float)noise.eval(0, (i * 4) + 2)), new Vector2f(0, 0)));
		}
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
