package ecumene.exo.sim.abstractions.solar;

import java.util.HashMap;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RPoint;

public class ExoSolarObject extends RPoint implements IExoSolarObject {

	protected Vector2f force = new Vector2f(), gravity = new Vector2f();
	public float mass = 10f;
	public HashMap<String, Double> materials;
	
	public ExoSolarObject(Vector2f position, Vector2f startVelocity){
		super("Solar Object", position);
		this.position = position;
		this.force = startVelocity;
		materials = new HashMap<String, Double>();
	}

	public ExoSolarObject(Vector2f position, Vector2f startVelocity, float mass){
		this(position, startVelocity);
		this.mass = mass;
	}

	public ExoSolarObject(float mass){
		this(new Vector2f(), new Vector2f());
		this.mass = mass;
	}
	
	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector2f getVelocity() {
		return force;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public RPoint step(List<IExoSolarObject> objects) {
		for(int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).equals(  this )) {
				IExoSolarObject object2 = objects.get(i);
				force.add(calcGravity(object2.getPosition(), object2.getMass(),
						position, mass));

				if (((ExoSolarObject) objects.get(i)).collides(this)) {
					if (!(objects.get(i).getMass() > getMass())) {
						this.mass += objects.get(i).getMass();
						float currentMag = getVelocity().length();
						this.force.normalize();
						this.force.mul(currentMag - objects.get(i).getVelocity().length() * (objects.get(i).getMass() / getMass()));
						objects.remove(i);
					}
				}
			}
		}
		Vector2f velocity = new Vector2f(force);
		velocity.mul(1/mass);
		position.add(force);
		return this;
	}

	private Vector2f calcGravity(Vector2f object2pos, float object2mass, Vector2f position, float mass) {
		Vector2f distance = new Vector2f(position).sub(new Vector2f(object2pos)).negate();
		float gravity = ((6.67f * (float)Math.pow(10, -6)) * mass * object2mass) / (float) Math.pow(distance.length(), 2);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExoSolarObject that = (ExoSolarObject) o;

		if (Float.compare(that.mass, mass) != 0) return false;
		if (force != null ? !force.equals(that.force) : that.force != null) return false;
		return gravity != null ? gravity.equals(that.gravity) : that.gravity == null;
	}

	@Override
	public int hashCode() {
		int result = force != null ? force.hashCode() : 0;
		result = 31 * result + (gravity != null ? gravity.hashCode() : 0);
		result = 31 * result + (mass != +0.0f ? Float.floatToIntBits(mass) : 0);
		return result;
	}
}
