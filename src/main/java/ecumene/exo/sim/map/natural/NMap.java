package ecumene.exo.sim.map.natural;

/**
 * Represents a map of objects with natural numbers as identifier (grid)
 */
public class NMap {
	/**The map*/
	public NObject[][] map;

	/**Constructs an NMap
	 * @param map The map*/
	public NMap(NObject[][] map){
		this.map = map;
	}

	/**@return The map*/
	public NObject[][] getMap(){
		return map;
	}
	
}
