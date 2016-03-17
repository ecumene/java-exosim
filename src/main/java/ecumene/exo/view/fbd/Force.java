package ecumene.exo.view.fbd;

import org.joml.Vector2f;

public class Force {
    private float    mag;
    private float    angle;
    private Vector2f force;
    private String   name;

    public Force(String name, Vector2f force){
        this.name = name;
        set(force);
    }

    public void set(Vector2f vec){
        this.mag   = vec.length();
        this.angle = vec.angle(new Vector2f(1, 0)); // +X
    }

    public Vector2f getForce() {
        return force;
    }

    public float getMagnitude(){
        return mag;
    }

    public float getAngle() {
        return angle;
    }

    public String getName() {
        return name;
    }
}
