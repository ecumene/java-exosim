package ecumene.exo.view.fbd;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a freebody during a time frame of 1
 */
public class FreeBody {
    private List<Force>   forces;
    private FreeBodyShape shape;
    private float         mass;
    private Vector2f      fnet; // t = 1; so a-> = Fnet

    public FreeBody(FreeBodyShape shape, float mass, Force ... forces){
        this.forces = new ArrayList<>();
        fnet = new Vector2f();
        for(Force force : forces){
            this.forces.add(force);
            fnet.add(force.getForce());
        }
        this.shape  = shape;
        this.mass   = mass;
    }

    public Vector2f getFnet() {
        return fnet;
    }

    public float getMass() {
        return mass;
    }

    public FreeBodyShape getShape() {
        return shape;
    }

    public List<Force> getForces() {
        return forces;
    }
}
