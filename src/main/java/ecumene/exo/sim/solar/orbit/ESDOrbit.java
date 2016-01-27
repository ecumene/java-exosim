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
		super(new Vector2f(0, 0), identifier, color);
		object1 = p1;
		object2 = p2;
		this.ups = ups;
	}
	
	
	@Override
	public void step(List<IExoSolarObject> objects) {
		super.step(objects);
		Vector2f distance = new Vector2f(object1.getPosition()).sub(new Vector2f(object2.getPosition()));
		float stepInterp = ExoRuntime.INSTANCE.getContext().getStepInterp();
		this.disp = distance.perpendicular().mul((float) Math.sqrt((6.67f * (object2.getMass() + object1.getMass())) / distance.length()));
	}
	
	@Override
	public String getIdentifier() {
		return "Orbit";
	}
	
}
