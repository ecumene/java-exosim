package ecumene.exo.sim.solar.orbit;

import java.awt.Color;
import java.util.List;

import org.joml.Vector2f;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.solar.ExoSDisplacement;
import ecumene.exo.sim.solar.IExoSolarObject;

public class ESDOrbit extends ExoSDisplacement {
	private IExoSolarObject object1;
	private IExoSolarObject object2;
	private float           ups = 1;
	
	public ESDOrbit(String identifier, Color color, IExoSolarObject p1, IExoSolarObject p2, float ups){
		super(calcOrbit(p1, p2, ups), identifier, color);
		object1 = p1;
		object2 = p2;
		this.ups = ups;
	}
	
	
	@Override
	public void step(List<IExoSolarObject> objects) {
		super.step(objects);
		this.disp = calcOrbit(object1, object2, ups);
	}
	
	@Override
	public String getIdentifier() {
		return "Orbit";
	}
	
	public static float getDistance(float size, float eccentricity, float trueAnomaly){
		return (size * (1f - (float) Math.pow(eccentricity, 2))) / (1 + (eccentricity * (float)Math.cos(trueAnomaly)));
	}
	
	private static Vector2f calcOrbit(IExoSolarObject p1, IExoSolarObject p2, float ups){
		float stepInterp = ExoRuntime.INSTANCE.getContext().getStepInterp();
		return new Vector2f((p1.getPosition().sub(p2.getPosition()))).negate().perpendicular().normalize().mul(ups).mul(stepInterp);
	}
}
