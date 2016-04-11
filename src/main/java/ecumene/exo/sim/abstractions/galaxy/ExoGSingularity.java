package ecumene.exo.sim.abstractions.galaxy;

import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public class ExoGSingularity extends RPoint {

	public float mass;
	
	public ExoGSingularity(String name, float mass) {
		super(name);
		this.mass = mass;
	}
	
	private static Vector2f ZERO = new Vector2f();
	@Override
	public Vector2f getPosition() {
		return ZERO; // It's going to be at the center whether you like it or not!
	}
	
	public boolean collides(IExoGalaxyObject object2){
		float xDif = getPosition().x - object2.getPosition().x;
		float yDif = getPosition().y - object2.getPosition().y;
		return (xDif * xDif + yDif * yDif) < (getMass() + object2.getMass()) * (getMass() + object2.getMass());
	}
	
	public float getMass() {
		return mass;
	}
}
