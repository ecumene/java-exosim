package ecumene.exo.sim.solar.orbit;

import java.awt.Color;

import org.joml.Vector2f;

import ecumene.exo.sim.solar.ESDisplacement;
import ecumene.exo.sim.solar.IExoSolarObject;

public class ESDOrbit extends ESDisplacement {
	public ESDOrbit(String identifier, Color color, IExoSolarObject p1, IExoSolarObject p2, float ups){
		super(new Vector2f((p1.getPosition().sub(p2.getPosition()))).normalize().mul(ups).negate().perpendicular(), identifier, color);
	}
}
