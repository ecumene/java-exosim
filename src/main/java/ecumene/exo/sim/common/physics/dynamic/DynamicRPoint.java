package ecumene.exo.sim.common.physics.dynamic;

import ecumene.exo.sim.ISimStepListener;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import org.joml.Vector2f;

import java.util.List;

// Take some notes boys this is some grade A++ engineering
public class DynamicRPoint extends RPoint implements IDFBody, IDynamicComponent, ISimStepListener {
    protected FBody dynamics;
    protected Vector2f velocity;

    public boolean nonStationary = true;

    // So many constructors :O
    private DynamicRPoint(String name, Vector2f position){
        super(name, position);
        velocity = new Vector2f();
    }

    public DynamicRPoint(RPoint point, IDFBody body){
        this(point.name, point.position);
        this.dynamics = new FBody(body);
    }

    public DynamicRPoint(String name, Vector2f position, IDFBody body){
        this(name, position);
        this.dynamics = new FBody(body);
    }

    public DynamicRPoint(String name, Vector2f position, FreeBodyShape shape, float mass, Force ... forces){
        this(name, position);
        this.dynamics = new FBody(shape, mass, forces);
    }

    public DynamicRPoint(String name, Vector2f position, float mass, Force ... forces){
        this(name, position);
        this.dynamics = new FBody(FreeBodyShape.BALL, mass, forces);
    }

    @Override
    public void onStep(SimContext context, int step) {
        velocity.add(new Vector2f(dynamics.calcFnet()).mul(1/getMass()));
        if(nonStationary) position.add(velocity);
    }

    @Override
    public FBody getDynamicBody() {
        return dynamics;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public FreeBodyShape getShape() {
        return dynamics.shape;
    }

    @Override
    public float getMass() {
        return dynamics.getMass();
    }

    @Override
    public List<Force> getForces() {
        return dynamics.getForces();
    }

}
