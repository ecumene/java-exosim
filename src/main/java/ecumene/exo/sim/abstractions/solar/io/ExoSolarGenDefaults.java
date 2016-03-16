package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import org.jdom2.Element;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ExoSolarGenDefaults {
    public  Vector2f massRange   = new Vector2f( 0, 1);
    public  Vector2f radiusRange = new Vector2f(-1, 1);
    public  Vector2f angleRange  = new Vector2f( 0, 360);
    private Vector2f velocity    = new Vector2f();
    public ExoSolarDefaultObject defaults;

    public ExoSolarGenDefaults(ExoSolarDefaultObject defaults, Vector2f massRange, Vector2f radiusRange, Vector2f angleRange){
        if(massRange   != null) this.massRange   = massRange;
        if(radiusRange != null) this.radiusRange = radiusRange;
        if(angleRange  != null) this.angleRange  = angleRange;
        this.defaults = defaults;
    }

    // Fills in any missing data to the solar object
    public ExoSolarObject fillAndGen(){
        return null;
    }


    public Vector2f getMassRange() {
        return massRange;
    }
}
