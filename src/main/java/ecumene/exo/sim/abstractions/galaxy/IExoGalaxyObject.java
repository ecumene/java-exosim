package ecumene.exo.sim.abstractions.galaxy;

import org.joml.Vector2f;

import ecumene.exo.sim.map.real.RPoint;

public interface IExoGalaxyObject {
	public float    getMass();
	public String   getName();
	public Vector2f getPosition();
	public Vector2f getVelocity();
	public RPoint   step(ExoGalaxyMap map);
}
