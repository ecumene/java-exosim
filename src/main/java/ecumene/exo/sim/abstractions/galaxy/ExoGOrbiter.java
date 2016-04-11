package ecumene.exo.sim.abstractions.galaxy;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.Force;
import org.joml.Vector2f;

public class ExoGOrbiter extends FBody implements IExoGalaxyObject {

	private String name;
	private Vector2f velocity;
	private RPoint point;

	public ExoGOrbiter(String name, float mass, Vector2f position, Vector2f velocity) {
		super(FreeBodyShape.BALL, mass, new Force("F>g", new Vector2f()));
		this.name = name;
		this.point = new RPoint(name, position);
		this.velocity = velocity;
	}
	
	@Override
	public RPoint step(ExoGalaxyMap map) {
		this.forces.set(0, new Force("F>g",
				calcGravity(ExoRuntime.INSTANCE.getContext().getGalaxy().getMap().singularity.getPosition(),
				            ExoRuntime.INSTANCE.getContext().getGalaxy().getMap().singularity.getMass(),
				            point.position, mass)));
		velocity.add(calcAcceleration());
		point.position.add(velocity);

		// Singularity Collision
		if(Math.pow((this.point.position.x - map.getSingularity().position.x), 2) +
				Math.pow((this.point.position.y - map.getSingularity().position.y), 2) <
				Math.pow((map.getSingularity().mass), 2))
			map.getOrbiters().remove(this);

		return point;
	}

	private Vector2f calcGravity(Vector2f object2pos, float object2mass, Vector2f position, float mass) {
		Vector2f distance = new Vector2f(position).sub(new Vector2f(object2pos)).negate();
		float gravity = ((6.67f * (float)Math.pow(10, -10)) * mass * object2mass) / (float) Math.pow(distance.length(), 2);
		distance.mul(gravity);

		return distance;
	}

	@Override
	public Vector2f getPosition() {
		return point.position;
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
