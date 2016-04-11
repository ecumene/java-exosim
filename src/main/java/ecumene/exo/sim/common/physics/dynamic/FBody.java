package ecumene.exo.sim.common.physics.dynamic;

import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.IFBody;
import ecumene.exo.view.fbd.FBDViewer;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FBody implements IFBody {
    public float         mass;
    public List<Force>   forces;
    public FreeBodyShape shape;

    public FBody(FreeBodyShape shape, float mass, Force ... forces){
        this.forces = Arrays.asList(forces);
        this.shape  = shape;
        this.mass   = mass;
    }

    public FBody(FBody body){
        this.forces = new ArrayList<>(body.forces);
        this.shape  = body.shape;
        this.mass   = body.mass;
    }

    public Vector2f calcFnet(){
        Vector2f out = new Vector2f();
        for(Force force : forces){
            out.add(force.getForce());
        }
        return out;
    }

    public Vector2f calcAcceleration(){
        return calcFnet().mul(1/mass);
    }

    public float getMass() {
        return mass;
    }

    public List<Force> getForces() {
        return forces;
    }

    public FreeBodyShape getShape() {
        return shape;
    }
}
