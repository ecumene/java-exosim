package ecumene.exo.sim.map.real;

public class RMap {
	public RPoint[] map;
	public int width, height;
	
	public RMap(RPoint ... map) {
		this.map = map;
	}
	
	public RPoint[] getMap(){
		return map;
	}
}
