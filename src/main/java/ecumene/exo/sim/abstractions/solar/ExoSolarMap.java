package ecumene.exo.sim.abstractions.solar;

import java.util.ArrayList;
import java.util.List;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RMap;
import ecumene.exo.sim.common.map.real.RPoint;

public class ExoSolarMap {
	private RMap                  solarSystem;
	private List<IExoSolarObject> objects;
	public static float GRAVITY = (6.67f * (float)Math.pow(10, -5)); // Really 6.67x10^-11
	
	public ExoSolarMap() { 
		objects = new ArrayList<>();
	}

	public ExoSolarMap(List<IExoSolarObject> objects) {
		this.objects = new ArrayList<>(objects);
	}
	
	public List<IExoSolarObject> getObjects(){
		return objects;
	}

	public void addObject(IExoSolarObject object){
		if(object instanceof ExoSolarObject) ((ExoSolarObject) object).setMap(this);
		objects.add(object);
	}

	public void removeObject(IExoSolarObject object){
		objects.remove(object);
	}
	
	public RMap step(SimContext context, int steps){
		RPoint[] points = new RPoint[objects.size()];
		
		for(int i = 0; i < objects.size(); i++) {
			((ExoSolarObject) objects.get(i)).setMap(this);
			points[i] = objects.get(i).step(context, steps, objects);
		}
		solarSystem = new RMap(points);
		return solarSystem;
	}
}
