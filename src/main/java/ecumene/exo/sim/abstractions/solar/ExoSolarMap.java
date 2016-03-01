package ecumene.exo.sim.abstractions.solar;

import java.util.ArrayList;
import java.util.List;

import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class ExoSolarMap {
	private ExoSolarObject        central;
	private RMap                  solarSystem;
	private List<IExoSolarObject> objects;
	public static float           G = 6.67f; // Really 6.67x10^-11
	
	public ExoSolarMap() { 
		objects = new ArrayList<IExoSolarObject>();
	}

	public ExoSolarMap(List<IExoSolarObject> objects) {
		this.objects = objects;
	}
	
	public List<IExoSolarObject> getObjects(){
		return objects;
	}
	
	public RMap step(){
		RPoint[] points = new RPoint[objects.size()];
		
		for(int i = 0; i < objects.size(); i++)
			points[i] = objects.get(i).step(objects);
		
		solarSystem = new RMap(points);
		return solarSystem;
	}
}
