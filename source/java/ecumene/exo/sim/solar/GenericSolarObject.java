package ecumene.exo.sim.solar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.joml.Vector2f;

import ecumene.exo.ExoRuntime;
import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;

public class GenericSolarObject extends RObject implements IExoSolarObject {

	protected Vector2f velocity = new Vector2f(), position = new Vector2f(), gravity = new Vector2f();
	protected List<ESDisplacement> displacements = new ArrayList<ESDisplacement>();
	public float mass = 0.01f;
	
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
		velocity = new Vector2f();                             // Set vector to new
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
		for(ESDisplacement displacement : displacements){
			displacement.step(objects);
		}

		this.position = new Vector2f(position);
		this.velocity = new Vector2f(getVelocity());
		gravity = this.recalcESD(objects);
		this.velocity.add(gravity);
		this.position.x += velocity.x;
		this.position.y += velocity.y;
//		this.velocity = new Vector2f();
		
		return new RPoint(this, position);
	}

	@Override
	public List<ESDisplacement> getDisplacements() {
		return displacements;
	}
	
	public void addDisplacement(ESDisplacement displacement){
		displacements.add(displacement);
	}
	
	public void removeDisplacement(ESDisplacement displacement){
		displacements.remove(displacement);
	}
	
	public Vector2f getLastGravity(){
		return gravity;
	}

	@Override
	public Vector2f recalcESD(List<IExoSolarObject> objects) {
		Vector2f vector = new Vector2f();
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i) == this) continue;
			Vector2f distance = new Vector2f(position).sub(new Vector2f(objects.get(i).getPosition())).negate();
			float gravity = ((6.67f) * mass * objects.get(i).getMass()) / (float) Math.pow(distance.length(), 2);
			distance.mul(gravity * ExoRuntime.INSTANCE.getContext().getStepInterp());
			vector.add(distance);
		}
		
		return vector;
	}
	@Override
	public String getName(int id) {
		return position.toString();
	}
}
