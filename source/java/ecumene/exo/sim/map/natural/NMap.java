package ecumene.exo.sim.map.natural;

public class NMap {
	public NObject[][] map;
	
	public NMap(NObject[][] map){
		this.map = map;
	}
	
	public NObject[][] getMap(){
		return map;
	}
	
}
