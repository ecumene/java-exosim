package ecumene.exo.sim.map.real;

public class RMap {
	protected RPoint[] map;
	protected int width, height;
	
	public RMap(RPoint ... map) {
		this.map = map;
	}
	
	public RPoint[] getMap(){
		return map;
	}
}
