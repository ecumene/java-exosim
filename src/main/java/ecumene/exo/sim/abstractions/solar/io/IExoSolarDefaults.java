package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import org.joml.Vector2f;

public interface IExoSolarDefaults {
    public String   getName();
    public float    getMass();
    public Vector2f getPosition();

    // Takes a partially filled object and fills the rest with
    // the default values.
    public ExoSolarObject fill(XMLSolarObject partialObject);
}
