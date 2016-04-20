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

	private List<ExoGCluster> clusters;
	private List<ExoGOrbiter> orbiters;

	public ExoGalaxyMap(ExoGSingularity singularity, ExoGCluster... clusters) {
		this.singularity = singularity;
		this.orbiters = new ArrayList<>();
		this.clusters = new LinkedList<>();
		for (ExoGCluster cluster : clusters)
			this.addCluster(cluster);
	}

	public ExoGSingularity getSingularity() {
		return singularity;
	}

	public void addCluster(ExoGCluster cluster) {
		for (ExoGOrbiter orbiter : cluster.getOrbiters()){
			orbiter.setCluster(cluster);
			orbiter.setMap(this);
			orbiters.add(orbiter);
		}
	}

	public List<ExoGCluster> getClusters() {
		return clusters;
	}

	public List<ExoGOrbiter> getOrbiters() {
		return orbiters;
	}

	public RMap step(SimContext context, int step) {
		RPoint[] points = new RPoint[orbiters.size() + 1];
		points[0] = singularity;
		for(int i = 0; i < orbiters.size(); i++){
			assert orbiters.get(i) != null;
			points[i + 1] = orbiters.get(i);
			orbiters.get(i).onStep(context, step);
		}
		return new RMap(points);
	}
}
