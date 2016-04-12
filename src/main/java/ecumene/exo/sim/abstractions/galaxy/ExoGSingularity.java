package ecumene.exo.sim.abstractions.galaxy;

import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.DynamicRPoint;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public class ExoGSingularity extends DynamicRPoint {

	
	public ExoGSingularity(String name, float mass) {
		super(name, new Vector2f(), new FBody(FreeBodyShape.BALL, mass));
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
}
