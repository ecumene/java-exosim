package ecumene.exo.sim.solar;

import java.awt.Color;

import org.joml.Vector2f;

public class ESDisplacement {
	public Vector2f disp;
	public String   identifier;
	public Color    color;
	
	// Exo-Solar Displacement
	// Contains everything needed to render a vector
	// I hate doing this kind of stuff. So... proprietary.
	public ESDisplacement(Vector2f disp, String identifier, Color color) {
		this.disp       = disp;
		this.identifier = identifier;
		this.color      = color;
	}

	public Vector2f getDisplacement() {
		return disp;
	}

	public void setDisplacement(Vector2f disp) {
		this.disp = disp;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
