package ecumene.exo.sim.galaxy;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.map.real.RPoint;
import org.joml.Vector2f;

import javax.xml.bind.util.ValidationEventCollector;

public class ExoGOrbiter extends RPoint implements IExoGalaxyObject {

	public float    mass = 1;
	public Vector2f velocity = new Vector2f(0, 0);
	
	public ExoGOrbiter(String name) {
		super(name);
		this.name = name;
	}
	
	@Override
	public RPoint step(ExoGalaxyMap map) {
		for(ExoGOrbiter neighbor : map.getOrbiters()) {
			velocity.add(new Vector2f(calcGravity(neighbor.getPosition(), neighbor.mass, getPosition(), getMass())));
		}
		velocity.add(new Vector2f(calcGravity(ExoRuntime.INSTANCE.getContext().getGalaxy().getMap().singularity.getPosition(),
				ExoRuntime.INSTANCE.getContext().getGalaxy().getMap().singularity.getMass(),
				position, mass)));
		this.position.add(velocity);
		if(Math.pow((this.position.x - map.getSingularity().position.x), 2) +
				Math.pow((this.position.y - map.getSingularity().position.y), 2) <
				Math.pow((map.getSingularity().mass), 2))
			map.getOrbiters().remove(this);

		return this;
	}

	private Vector2f calcGravity(Vector2f object2pos, float object2mass, Vector2f position, float mass) {
		Vector2f distance = new Vector2f(position).sub(new Vector2f(object2pos)).negate();
		float gravity = ((6.67f) * mass * object2mass) / (float) Math.pow(distance.length(), 2);
		distance.mul(gravity);
		distance.x /= mass; // Force to velocity
		distance.y /= mass; // Force to velocity

		return distance;
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
