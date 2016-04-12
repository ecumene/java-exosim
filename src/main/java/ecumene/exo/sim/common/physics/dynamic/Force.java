package ecumene.exo.sim.common.physics.dynamic;

import org.joml.Vector2f;

public class Force {
    protected Vector2f force;
    protected String   name;

    public Force(String name, Vector2f force){
        this.name  = name;
        this.force = force;
    }

    public Force(Force force){
        this.name = force.getName();
        this.force = new Vector2f(force.getForce());
    }

    public Vector2f getForce() {
            return force;
        }

    public float getMagnitude(){
            return force.length();
        }

    public float getAngle() {
            return force.angle(new Vector2f(1, 0));
        }

    public String getName() {
            return name;
        }

}
