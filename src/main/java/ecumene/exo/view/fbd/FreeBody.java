package ecumene.exo.view.fbd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FreeBody {
    private List<Force>   forces;
    private FreeBodyShape shape;
    private float         mass;

    public FreeBody(FreeBodyShape shape, float mass, Force ... forces){
        this.forces = Arrays.asList(forces);
        this.shape  = shape;
        this.mass   = mass;
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
