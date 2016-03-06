package ecumene.exo.sim.map.real;

/**Represents a map of objects with position being floats (real numbers)*/
public class RMap {
	protected RPoint[] map;     // Map

	/**
	 * Constructs the RMap
	 * @param map The map of objects
     */
	public RMap(RPoint ... map) {
		this.map = map;
	}

	/**@return The final map*/
	public RPoint[] getMap(){
		return map;
	}
}
