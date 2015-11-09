package ecumene.exo.sim;

public class ESContext {
	private long seed;
	
	public ESContext(long seed) {
		this.seed = seed;
	}
	
	public long getSeed(){
		return seed;
	}
}
