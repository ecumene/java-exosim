package ecumene.exo.sim.abstractions.galaxy;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RMap;
import ecumene.exo.sim.common.map.real.RPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ExoGalaxyMap {
	
	public ExoGSingularity singularity;
	public List<ExoGOrbiter> orbiters;
	
	public ExoGalaxyMap(ExoGSingularity singularity, ExoGOrbiter ... orbiters) {
		this.singularity = singularity;
		this.orbiters = new LinkedList<>(Arrays.asList(orbiters));
	}

	public ExoGSingularity getSingularity() {
		return singularity;
	}
	
	public List<ExoGOrbiter> getOrbiters() {
		return orbiters;
	}
	
	public RMap step(SimContext context, int step) {
		RPoint[] points = new RPoint[orbiters.size() + 1];
		points[0] = singularity;
		for(int i = 0; i < orbiters.size(); i++){
			assert orbiters.get(i) != null;
			orbiters.get(i).onStep(context, step);
			points[i + 1] = orbiters.get(i);
		}
		return new RMap(points);
	}
}
