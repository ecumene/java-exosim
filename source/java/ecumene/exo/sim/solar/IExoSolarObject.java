package ecumene.exo.sim.solar;

import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RPoint;

public interface IExoSolarObject {
	public float getMass();
	
	public Vector2f             getVelocity();
	public Vector2f             getPosition();
	public List<ESDisplacement> getDisplacements();
	
	public RPoint step(List<IExoSolarObject> objects);
}
