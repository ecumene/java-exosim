package ecumene.exo.sim.abstractions.solar;

import java.util.HashMap;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public class ExoSolarObject extends RPoint implements IExoSolarObject {

	protected Vector2f velocity = new Vector2f();
	public float mass = 10f;
	public HashMap<String, Double> materials;

	public ExoSolarObject(String name, Vector2f position, Vector2f startVelocity, float mass){
		super(name, position);
		this.velocity = startVelocity;
		materials = new HashMap();
		this.mass = mass;
	}

	public ExoSolarObject(float mass){
		this("Object", new Vector2f(), new Vector2f(), mass);
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
		for(int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).equals(this)) {
				IExoSolarObject object2 = objects.get(i);
				velocity.add(calcGravity(object2.getPosition(), object2.getMass(), position, mass));

				if (((ExoSolarObject) objects.get(i)).collides(this)) {
					if (!(objects.get(i).getMass() > getMass())) {
						this.mass += objects.get(i).getMass();
						float currentMag = getVelocity().length();
						this.velocity.normalize();
						this.velocity.mul(currentMag - objects.get(i).getVelocity().length() * (objects.get(i).getMass() / getMass()));
						objects.remove(i);
					}
				}
			}
		}
		Vector2f velocity = new Vector2f(this.velocity);
		velocity.mul(1/mass);
		position.add(this.velocity);
		return this;
	}

	private Vector2f calcGravity(Vector2f object2pos, float object2mass, Vector2f position, float mass) {
		Vector2f distance = new Vector2f(position).sub(new Vector2f(object2pos)).negate();
		float gravity = (ExoSolarMap.G * mass * object2mass) / (float) Math.pow(distance.length(), 2);
		gravity /= mass;
		distance.mul(gravity);

		return distance;
	}

	public boolean collides(IExoSolarObject object2){
		float xDif = getPosition().x - object2.getPosition().x;
		float yDif = getPosition().y - object2.getPosition().y;
		return (xDif * xDif + yDif * yDif) < (getMass() + object2.getMass()) * (getMass() + object2.getMass());
	}
		
	@Override
	public String getName(int id) {
		return "Object: " + id;
	}

}
