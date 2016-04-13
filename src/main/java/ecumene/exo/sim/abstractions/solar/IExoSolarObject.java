package ecumene.exo.sim.abstractions.solar;

import java.util.List;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.physics.dynamic.IDynamicComponent;
import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public interface IExoSolarObject extends IDynamicComponent {
	public float getMass();
	
	public Vector2f getVelocity();
	public Vector2f getPosition();

	public RPoint step(SimContext context, int simSteps, List<IExoSolarObject> objects);
}
