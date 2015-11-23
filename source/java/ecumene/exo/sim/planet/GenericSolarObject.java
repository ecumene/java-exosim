package ecumene.exo.sim.planet;

import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.IExoSolarObject;

public class GenericSolarObject extends RObject implements IExoSolarObject {

	protected Vector2f velocity = new Vector2f(), position = new Vector2f();
	protected float mass;
	
	public GenericSolarObject() { }
	
	public GenericSolarObject(Vector2f position, Vector2f startVelocity){
		this.position = position;
		this.velocity = startVelocity;
	}
	
	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector2f getVelocity() {
		return velocity;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public RPoint step(List<IExoSolarObject> objects) {
		System.out.println("step");
		return new RPoint(this, position);
	}
	
}
