package ecumene.exo.sim.planet;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RObject;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.ESDisplacement;
import ecumene.exo.sim.solar.IExoSolarObject;

public class GenericSolarObject extends RObject implements IExoSolarObject {

	protected Vector2f velocity = new Vector2f(), position = new Vector2f();
	protected List<ESDisplacement> displacements = new ArrayList<ESDisplacement>();
	protected float mass = 0.5f;
	
	public GenericSolarObject() { }
	
	public GenericSolarObject(Vector2f position, Vector2f startVelocity){
		this.position = position;
		this.velocity = startVelocity;
		this.addDisplacement(new ESDisplacement(new Vector2f(0.2f, 0), "d1", new Color(255, 0, 0)));
		this.addDisplacement(new ESDisplacement(new Vector2f(-0.2f, 0), "d2", new Color(0, 0, 255)));
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
		this.position = new Vector2f(position);
		this.position.x += velocity.x;
		this.position.y += velocity.y;
		
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
}
