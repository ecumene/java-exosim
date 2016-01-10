package ecumene.exo.sim;

import ecumene.exo.sim.solar.ExoSolarMap;

public class SimSolarContext {
	
    private SimGalaxyContext parentGalaxy;
    private ExoSolarMap     solarMap;
    private int             index;
    private int             follow;
    
    public SimSolarContext(SimGalaxyContext galaxy, int index, ExoSolarMap map) {
    	this.parentGalaxy = galaxy;
    	this.solarMap     = map;
    	this.index        = index;
    }
    
    public SimGalaxyContext getGalaxy() {
		return parentGalaxy;
	}
    
    public ExoSolarMap getSolarMap() {
		return solarMap;
	}
    
    public int getFollowing(){
    	return follow;
    }
    
    public void setFollow(int follow) {
		this.follow = follow;
	}
}
