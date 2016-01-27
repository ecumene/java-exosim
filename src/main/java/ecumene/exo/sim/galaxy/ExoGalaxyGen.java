package ecumene.exo.sim.galaxy;

import org.joml.Vector2f;

import ecumene.exo.runtime.OpenSimplexNoise;

public class ExoGalaxyGen {
	
	private OpenSimplexNoise noise;
	
	public ExoGalaxyGen(long seed){
		noise = new OpenSimplexNoise(seed);
	}
	
	public OpenSimplexNoise getNoise() {
		return noise;
	}
	
	public IExoGalaxySource genGalaxy(float singularityMass, float maxOrbiterMass, float minOrbiterMass,
			                          int orbiterNum,        int size){
		ExoGSingularity singularity = new ExoGSingularity("Singularity", singularityMass);
		ExoGOrbiter[] orbiters = new ExoGOrbiter[orbiterNum];
		
		for(int i = 0; i < orbiters.length; i++){
			ExoGOrbiter orbiter = new ExoGOrbiter("Orbiter ");
			orbiter.mass = (float) (noise.eval(0, i) * maxOrbiterMass) + minOrbiterMass; // y=mx+b ;)
			Vector2f position = new Vector2f((float)noise.eval(1, (i * 2) + 0) * size, 
					                         (float)noise.eval(1, (i * 2) + 1) * size);
			orbiter.position = position;
			orbiters[i] = orbiter;
		}
		
		ExoGalaxyMap map = new ExoGalaxyMap(singularity, orbiters);
		
		return new IExoGalaxySource() {
			@Override
			public ExoGalaxyMap getSource() {
				return map;
			}
		};
	}
}
