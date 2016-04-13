package ecumene.exo.sim.abstractions.planet;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.DynamicRPoint;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.Gravity;
import org.joml.Vector2f;

public class ExoPlanetMoon extends DynamicRPoint implements IExoPlanetObject {
    private ExoPlanet planet;    // Planet

    public ExoPlanetMoon(String name, float mass, float r, float angle, Vector2f v1){
        super(name, new Vector2f((float) (r * Math.cos(Math.toRadians(angle))),
                                 (float) (r * Math.sin(Math.toRadians(angle)))),
                new FBody(FreeBodyShape.BALL, mass));
        nonStationary = true;
        velocity = v1;
    }

    @Override
    public void onStep(SimContext context, int step) {
        super.onStep(context, step);
    }

    public boolean hasTriggeredRevolution(){
        float angle = (float) Math.toDegrees(new Vector2f(1, 0).angle(position));
        return (-90 <= angle && angle <= 90);
    }

    public ExoPlanet getPlanet() {
        return planet;
    }

    public ExoPlanetMoon setParent(ExoPlanet planet){
        this.getForces().clear();
        this.dynamics.forces.add(new Gravity(this, planet, planet.getGravityInfluence()));
        this.planet = planet;
        for(ExoPlanetMoon otherMoon : planet.getMoonList())
            if(otherMoon != this) this.dynamics.forces.add(new Gravity(this, otherMoon, planet.getGravityInfluence()));
        return this;
    }

    @Override
    public FBody getDynamicBody() {
        return dynamics;
    }

}
