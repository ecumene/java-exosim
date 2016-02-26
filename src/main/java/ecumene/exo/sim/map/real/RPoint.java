package ecumene.exo.sim.map.real;

import org.joml.Vector2f;

public class RPoint {
	public Vector2f position;
	public String   name = "Default RObject";

	public RPoint(String name, RPoint point) {
		this.name = name;
		this.position = new Vector2f(point.position);
	}
	
	public RPoint(String name, Vector2f position) {
		this.position = position;
		this.name = name;
	}
	
	public RPoint(String name) {
		this.position = new Vector2f();
		this.name = name;
	}
	
	public Vector2f getPosition(){
		return position;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(int id) {
		return name;
	}

}
