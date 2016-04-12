package ecumene.exo.sim.abstractions.galaxy;

import ecumene.exo.sim.ISimStepListener;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.IDynamicComponent;
import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public interface IExoGalaxyObject extends IDynamicComponent, ISimStepListener {
	public float    getMass();
	public String   getName();
	public Vector2f getPosition();
	public Vector2f getVelocity();

}
