package ecumene.exo.sim.galaxy;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RPoint;

public class ExoGOrbiter extends RPoint implements IExoGalaxyObject {

	public float    mass;
	public Vector2f velocity = new Vector2f(1, 1);
	
	public ExoGOrbiter(String name) {
		super(name);
		this.name = name;
	}
	
	@Override
	public RPoint step(ExoGalaxyMap map) {
		this.position.add(velocity);
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public float getMass() {
		return mass;
	}

}
