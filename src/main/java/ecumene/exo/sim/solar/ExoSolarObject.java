package ecumene.exo.sim.solar;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.map.real.RPoint;

public class ExoSolarObject extends RPoint implements IExoSolarObject {

	protected Vector2f velocity = new Vector2f(), gravity = new Vector2f();
	protected List<ExoSDisplacement> displacements = new ArrayList<ExoSDisplacement>();
	public float mass = 5f;
	
	public ExoSolarObject(Vector2f position, Vector2f startVelocity){
		super("Solar Object", position);
		this.position = position;
		this.velocity = startVelocity;
	}
	
	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public Vector2f getVelocity() {
		for(int i = 0; i < displacements.size(); i++)          // Iterator
			velocity = velocity.add(displacements.get(i).disp);// Summation of ES-Displacements
		
		return velocity;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public RPoint step(List<IExoSolarObject> objects) {
		for(int i = 0; i < objects.size(); i++) 
		    if(objects.get(i) != this){
    		    if(((ExoSolarObject) objects.get(i)).collides(this)){
	    		    if(!(objects.get(i).getMass() > getMass()))
	    		    {
						this.mass += objects.get(i).getMass();
    		    		float currentMag = getVelocity().length();
    		    		this.velocity.normalize();
    		    		this.velocity.mul(currentMag - objects.get(i).getVelocity().length() * (objects.get(i).getMass() / getMass()));
						objects.remove(i);
    		    	}
    		    }
		    }
	    for(ExoSDisplacement displacement : displacements) displacement.step(objects);

		this.position = new Vector2f(position);
		this.velocity = new Vector2f(getVelocity());
		this.velocity.add(this.calcGravity(objects, position, getMass()));
		this.position.add(velocity);
		return this;
	}

	@Override
	public List<ExoSDisplacement> getDisplacements() {
		return displacements;
	}
	
	public void addDisplacement(ExoSDisplacement displacement){
		displacements.add(displacement);
	}
	
	public void removeDisplacement(ExoSDisplacement displacement){
		displacements.remove(displacement);
	}
	
	public Vector2f getLastGravity(){
		return gravity;
	}

	public Vector2f calcGravity(List<IExoSolarObject> objects, Vector2f position, float mass) {
		Vector2f vector = new Vector2f();
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i) == this) continue;
			if(objects.get(i) == null) continue;
			Vector2f distance = new Vector2f(position).sub(new Vector2f(objects.get(i).getPosition())).negate();
			float gravity = ((6.67f) * mass * objects.get(i).getMass()) / (float) Math.pow(distance.length(), 2);
			distance.mul(gravity * ExoRuntime.INSTANCE.getContext().getStepInterp());
			distance.x /= mass;
			distance.y /= mass;
			vector.add(distance);
		}
		
		return vector;
	}
	
	public boolean collides(IExoSolarObject object2){
		float xDif = getPosition().x - object2.getPosition().x;
		float yDif = getPosition().y - object2.getPosition().y;
		return (xDif * xDif + yDif * yDif) < (getMass() + object2.getMass()) * (getMass() + object2.getMass());
	}
		
	@Override
	public String getName(int id) {
		return position.toString();
	}
}
