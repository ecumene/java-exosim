package ecumene.exo.sim.abstractions.solar;

import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public interface IExoSolarObject {
	public float getMass();
	
	public Vector2f getVelocity();
	public Vector2f getPosition();

	public RPoint step(List<IExoSolarObject> objects);
}
