package ecumene.exo.sim.galaxy;

import org.joml.Vector2f;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import ecumene.exo.OpenSimplexNoise;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;
import ecumene.exo.sim.solar.ExoSolarMap;

public class ExoGalaxyMap extends RMap {
	
	private OpenSimplexNoise noise;
	
	public ExoGalaxyMap(long seed) {
		noise = new OpenSimplexNoise(seed);
		int size = (int) Math.abs(noise.eval(1, 0) * 1000);
		map = new RPoint[size];
		for(int i = 0; i < size; i++){
			float angle  = (float) (noise.eval(0, i * 2 + 1) * Math.PI * 2);
			float radius = (float) (noise.eval(0, i * 2 + 2) * Math.PI * 2) * 30;
			
			Vector2f vector = new Vector2f(
					(float) Math.sin(angle) * radius,
					(float) Math.cos(angle) * radius);
			map[i] = new RPoint(new ExoSolarMap(), vector); 
		}
	}
	
	// Depreciated because people can easily add non-solar
	// system r-objects, which is bad for your health
	@Deprecated
	@Override
	public RPoint[] getMap() {
		return super.getMap();
	}
}
