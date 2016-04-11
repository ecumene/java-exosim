package ecumene.exo.sim.common.map.real;

import org.joml.Vector2f;

/**Represents a single RMap point*/
public class RPoint {
	/**The points position*/
	public Vector2f position;
	/**The points name*/
	public String   name = "Default RObject";

	/**
	 * Constructs an RPoint
	 * @param name  The rpoints name
	 * @param point The rpoint
     */
	public RPoint(String name, RPoint point) {
		this.name = name;
		this.position = new Vector2f(point.position);
	}

	/**
	 * Constructs an RPoint
	 * @param name     The points name
	 * @param position The points position
     */
	public RPoint(String name, Vector2f position) {
		this.position = position;
		this.name = name;
	}

	/**
	 * Constructs an RPoint
	 * @param name     The points name
	 */
	public RPoint(String name) {
		this.position = new Vector2f();
		this.name = name;
	}

	/**@return The points position */
	public Vector2f getPosition(){
		return position;
	}

	/**@param name The points new name*/
	public void setName(String name){
		this.name = name;
	}

	/**@return The points name*/
	public String getName(int id) {
		return name;
	}

}
