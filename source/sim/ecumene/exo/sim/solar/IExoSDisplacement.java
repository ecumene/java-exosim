package ecumene.exo.sim.solar;

import java.awt.Color;

import org.joml.Vector2f;

public interface IExoSDisplacement {
	public Vector2f getDisplacement();
	public String   getIdentifier();
	public Color    getColor();
}
