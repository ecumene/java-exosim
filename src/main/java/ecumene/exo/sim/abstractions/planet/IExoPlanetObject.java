package ecumene.exo.sim.abstractions.planet;

import ecumene.exo.sim.common.physics.dynamic.IDynamicComponent;
import org.joml.Vector2f;

public interface IExoPlanetObject extends IDynamicComponent {
    public float getMass();
    public Vector2f getPosition();
}
