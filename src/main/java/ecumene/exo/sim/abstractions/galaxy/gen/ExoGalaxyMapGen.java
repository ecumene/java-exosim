package ecumene.exo.sim.abstractions.galaxy.gen;

import ecumene.exo.sim.abstractions.galaxy.ExoGOrbiter;
import ecumene.exo.sim.abstractions.galaxy.ExoGSingularity;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import org.joml.Vector2f;

import ecumene.exo.utils.OpenSimplexNoise;

public class ExoGalaxyMapGen {
	
	private OpenSimplexNoise noise;
	
	public ExoGalaxyMapGen(long seed){
		noise = new OpenSimplexNoise(seed);
	}
	
	public OpenSimplexNoise getNoise() {
		return noise;
	}
	
	public IExoGalaxyMapSource genGalaxy(float singularityMass, float maxOrbiterMass, float minOrbiterMass,
										 int orbiterNum, int size){
		ExoGSingularity singularity = new ExoGSingularity("Singularity", singularityMass);
		ExoGOrbiter[] orbiters = new ExoGOrbiter[orbiterNum];
		
		for(int i = 0; i < orbiters.length; i++){
			float angle  =  (float)noise.eval(1, (i * 2) + 0) * 360f;
			float radius = ((float)noise.eval(1, (i * 2) + 1) * size);
			Vector2f position = new Vector2f(radius * (float)Math.cos(angle),
					                         radius * (float)Math.sin(angle));
			ExoGOrbiter orbiter = new ExoGOrbiter("Solar System ", position);
			orbiter.mass = (float) (noise.eval(0, i) * maxOrbiterMass) + minOrbiterMass; // y=mx+b ;)
			orbiters[i] = orbiter;
		}
		
		ExoGalaxyMap map = new ExoGalaxyMap(singularity, orbiters);
		
		return new IExoGalaxyMapSource() {
			@Override
			public ExoGalaxyMap getSource() {
				return map;
			}
		};
	}
}
