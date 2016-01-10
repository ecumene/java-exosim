package ecumene.exo.sim;

import ecumene.exo.sim.galaxy.ExoGalaxyMap;

public class SimGalaxyContext {
	
	private SimContext    parent;
	private ExoGalaxyMap map;
	
	public SimGalaxyContext(SimContext parent, ExoGalaxyMap map) {
		this.parent = parent;
		this.map    = map;
	}
	
	public ExoGalaxyMap getMap() {
		return map;
	}
	
	public SimContext getParent() {
		return parent;
	}
	
}
