package ecumene.exo.sim.planet;

import ecumene.exo.sim.map.real.RPoint;
import org.joml.Vector2f;

public class ExoPlanetMoon implements IExoPlanetObject {
    private float     mass;  // Ratio between moon's mass and the planet's
    private ExoPlanet planet;// Planet
    private RPoint    point; // So... like... what's the point of this project anyway?
    private RPoint    lastPoint;
    private Vector2f velocity;

    public ExoPlanetMoon(float mass, float r, float angle, Vector2f v1){
        this.mass = mass;
        point = new RPoint("Moon", new Vector2f((float) (r * Math.cos(Math.toRadians(angle))),
                                                (float) (r * Math.sin(Math.toRadians(angle)))));
        velocity = new Vector2f(v1);
    }

    public RPoint step(){
        velocity.add(calcAccelGrav(planet));
        point.position = point.position.add(velocity);
        lastPoint = point;
        return point;
    }

    public boolean hasTriggeredRevolution(){
        float angle = (float) Math.toDegrees(new Vector2f(1, 0).angle(point.position));
        return (-90 <= angle && angle <= 90);
    }

    public RPoint getLastPoint(){
        return lastPoint;
    }

    public ExoPlanet getPlanet() {
        return planet;
    }

    public ExoPlanetMoon setParent(ExoPlanet planet){
        this.planet = planet;
        return this;
    }

    private Vector2f calcAccelGrav(IExoPlanetObject center){
        Vector2f distance = new Vector2f(point.getPosition()).sub(new Vector2f(center.getPosition())).negate();// a -> = -(P1 - P2) = P2 - P1
        float gravity = (6.67E-3f * mass * center.getMass()) / (float) Math.pow(distance.length(), 2);         // |ag| = (G * M1 * M2) / (r^2)
        distance.mul(gravity);                                                                                 // a -> = normalize(a) * |ag|
        distance.x /= mass;                                                                                    // force / mass = acceleration
        distance.y /= mass;                                                                                    // highschool physics is working!

        return distance;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public Vector2f getPosition() {
        return point.getPosition();
    }
}
