package ecumene.exo.sim.planet;

import java.util.ArrayList;
import java.util.List;

import ecumene.exo.runtime.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.IExoSolarObject;

public class ExoPlanetMap {

	private List<IExoPlanetObject> objects;
	private RMap displayMap;
	public static float G = 6.67f; // Really 6.67x10^-11

	private ExoPlanetMap() {
		objects = new ArrayList<IExoPlanetObject>();
	}

	public ExoPlanetMap(ExoPlanet planet) {
		this();
		setPlanet(planet);
		for(int i = 0; i < planet.getMoonList().size(); i++){ // I know array.addAll(...) exists, but I need to set the parents also
			this.objects.add(planet.getMoonList().get(i));
			planet.getMoonList().get(i).setParent(planet);
		}
	}

	public ExoPlanet getPlanet(){
		return (ExoPlanet) objects.get(0);
	}

	public void setPlanet(ExoPlanet planet){
		planet.onAddedTo(this);
		objects.add(0, planet);
	}

	public List<IExoPlanetObject> getObjects(){
		return objects;
	}

	public RMap step(){
		RPoint[] points = new RPoint[((ExoPlanet) objects.get(0)).getMoonList().size() + 1]; // For all the moons + the planet
		points[0] = getPlanet().step();

		for(int i = 0; i < ((ExoPlanet) objects.get(0)).getMoonList().size(); i++)
			points[i + 1] = ((ExoPlanet) objects.get(0)).getMoonList().get(i).step(); // RPoint ID = Object list ID + 1

		displayMap = new RMap(points);
		return displayMap;
	}
}
