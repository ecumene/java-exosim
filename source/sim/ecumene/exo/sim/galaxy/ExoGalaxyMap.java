package ecumene.exo.sim.galaxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecumene.exo.sim.ISimStepListener;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class ExoGalaxyMap {
	
	public ExoGSingularity   singularity;
	public List<ExoGOrbiter> orbiters;
	
	public ExoGalaxyMap(ExoGSingularity singularity, ExoGOrbiter ... orbiters) {
		this.singularity = singularity;
		this.orbiters = new ArrayList<ExoGOrbiter>(Arrays.asList(orbiters));
	}
	
	public ExoGSingularity getSingularity() {
		return singularity;
	}
	
	public List<ExoGOrbiter> getOrbiters() {
		return orbiters;
	}
	
	public RMap step() {
		RPoint[] points = new RPoint[orbiters.size() + 1];
		points[0] = singularity;
		for(int i = 0; i < orbiters.size(); i++) points[i + 1] = orbiters.get(i).step(this);
		return new RMap(points);
	}
}
