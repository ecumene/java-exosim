package ecumene.exo.sim.solar;

import java.awt.Color;
import java.util.List;

import org.joml.Vector2f;

public class ESDisplacement implements IESDisplacement {
	public Vector2f disp;
	public String   identifier;
	public Color    color;
	
	// Exo-Solar Displacement
	// Contains everything needed to render a vector
	// I hate doing this kind of stuff. So... proprietary.
	public ESDisplacement(Vector2f disp, String identifier, Color color) {
		this.disp = disp;
		this.identifier = identifier;
		this.color      = color;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public Vector2f getDisplacement() {
		return disp;
	}
	
	@Override
	public String getIdentifier() {
		return "vector";
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void step(List<IExoSolarObject> objects){
	}

}
