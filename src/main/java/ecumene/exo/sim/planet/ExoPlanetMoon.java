package ecumene.exo.sim.planet;

import ecumene.exo.sim.map.real.RPoint;
import org.joml.Vector2f;

public class ExoPlanetMoon implements IExoPlanetObject {
    private float     mass;     // Ratio between moon's mass and the planet's
    private ExoPlanet planet;   // Planet
    private float     r;        // Distance from moon to planet
    private float     angle;    // Polar Angle coordinate
    private RPoint    lastPoint;// So... like... what's the point of this project anyway?

    public ExoPlanetMoon(float mass, float r, float angle){
        this.mass = mass;
        this.r = r;
        this.angle = angle;
    }

    public RPoint step(){
        RPoint out = new RPoint("Moon");
        out.position = new Vector2f((float) (r * Math.cos(angle)), (float) (r * Math.sin(angle)));
        out.position = new Vector2f(20, 20);
        lastPoint = out;
        return out;
    }

    public ExoPlanet getPlanet() {
        return planet;
    }

    public ExoPlanetMoon setParent(ExoPlanet planet){
        this.planet = planet;
        return this;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public Vector2f getPosition() {
        return lastPoint.getPosition();
    }
}
