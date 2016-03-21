package ecumene.exo.view.fbd;

import org.joml.Vector2f;

public class Force {
    private Vector2f force;
    private String   name;

    public Force(String name, Vector2f force){
        this.name  = name;
        this.force = force;
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
