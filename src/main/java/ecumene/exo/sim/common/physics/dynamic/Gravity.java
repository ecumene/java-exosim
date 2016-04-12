package ecumene.exo.sim.common.physics.dynamic;

import org.joml.Vector2f;

// The one, the only all encompassing class for all your pull-ey needs!
public class Gravity extends Force {
    public static final float G = 0.0000000000667F; // Dear god leave this alone, if you touch it it'll think
                                                    // it's an octet or something.
    private DynamicRPoint obj1, obj2;
    public Gravity(DynamicRPoint object1, DynamicRPoint object2){
        super("F>g", new Vector2f());
        this.obj1 = object1;
        this.obj2 = object2;
    }

    @Override
    public Vector2f getForce() {
        Vector2f.sub(obj1.getPosition(), obj2.getPosition(), force);
        return force.mul(getFGrav(obj1.getMass(), obj2.getMass(), force.length()));
    }

    public float getFGrav(float mass1, float mass2, float distance) {
        return (G * mass1 * mass2) / (distance * distance);
    }

}
