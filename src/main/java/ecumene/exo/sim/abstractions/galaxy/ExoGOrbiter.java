package ecumene.exo.sim.abstractions.galaxy;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.DynamicRPoint;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.Force;
import ecumene.exo.sim.common.physics.dynamic.Gravity;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.LinkedList;

public class ExoGOrbiter extends DynamicRPoint implements IExoGalaxyObject {

	private ExoGalaxyMap map;
	private ExoGCluster  cluster;

	public ExoGOrbiter(ExoGSingularity singularity, String name, float mass, Vector2f position, Vector2f velocity) {
		super(name, position, FreeBodyShape.BALL, mass, new Force("F>g", new Vector2f()));
		this.velocity = velocity;
		this.dynamics.forces.add(0, new Gravity(this, singularity));
	}

	public void setMap(ExoGalaxyMap map){
		this.map = map;
	}

	public void setCluster(ExoGCluster cluster){
		this.cluster = cluster;
	}

	public ExoGCluster getCluster() {
		return cluster;
	}

	@Override
	public void onStep(SimContext context, int step) {
		super.onStep(context, step);
		if(map != null)
			if(map.getSingularity().collides(this))
				map.getOrbiters().remove(this);
	}

	@Override
	public String getName() {
		return name;
	}
}
