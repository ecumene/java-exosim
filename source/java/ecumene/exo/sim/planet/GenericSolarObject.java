package ecumene.exo.sim.planet;

import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.IExoSolarObject;

public class GenericSolarObject extends RObject implements IExoSolarObject {

	protected Vector2f velocity, position;
	protected float mass;
	
	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector2f getVelocity() {
		return new Vector2f();
	}

	@Override
	public Vector2f getPosition() {
		return new Vector2f();
	}

	@Override
	public RPoint step(List<IExoSolarObject> objects) {
		System.out.println("step");
		return new RPoint(this, position);
	}
	
}
