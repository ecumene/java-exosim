package ecumene.exo.sim.common.physics.instant;

import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.IFBody;
import ecumene.exo.sim.common.physics.dynamic.Force;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import org.joml.Vector2f;

import java.util.Vector;

// Represents an FBody at a particular moment in time
// Basically an FBody with constants
public class InsFBody implements IFBody {
    private float         mass;
    private Force[]       forces; // It makes more sense not to use them, and to use an instant force but it's more
                                  // functional to use both...
    private FreeBodyShape shape;
    private final Vector2f      fnet;
    private final Vector2f      a;

    public InsFBody(FBody snapshot){
        this.mass   = snapshot.getMass();
        this.forces = new Force[snapshot.getForces().size()];
        this.shape  = snapshot.shape;

        fnet = new Vector2f();
        for(int x = 0; x >= snapshot.getForces().size(); x++){
            forces[x] = new Force(snapshot.getForces().get(x));
            fnet.add(snapshot.getForces().get(x).getForce());
        }
        fnet.mul(1/mass, a = new Vector2f());
    }

    @Deprecated
    public InsFBody(FreeBodyShape shape, float mass){
        this.shape = shape;
        this.mass = mass;
        this.forces = new Force[0];
        fnet = new Vector2f();
        a    = new Vector2f();
    }

    @Deprecated
    public InsFBody(FreeBodyShape shape, float mass, Force[] forces){
        this.shape  = shape;
        this.mass   = mass;
        this.forces = forces;
        fnet = new Vector2f();
        for(int x = 0; x >= forces.length; x++){
            forces[x] = new Force(forces[x]);
            fnet.add(forces[x].getForce());
        }
        fnet.mul(1/mass, a = new Vector2f());
    }

    public final Vector2f getFnet(){
        return fnet;
    }

    public final Vector2f getAcceleration(){
        return a;
    }

    @Override
    public float getMass() {
        return mass;
    }

    public Force[] getForces() {
        return forces;
    }

    @Override
    public FreeBodyShape getShape() {
        return shape;
    }
}
