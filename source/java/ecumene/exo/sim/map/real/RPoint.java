package ecumene.exo.sim.map.real;

import org.joml.Vector2f;

public class RPoint {
	public Vector2f position;
	public RObject object;
	
	public RPoint(RObject object, Vector2f position) {
		this.object = object;
		this.position = position;
	}
	
	public Vector2f getPosition(){
		return position;
	}
	
	public RObject getObject(){
		return object;
	}
}
