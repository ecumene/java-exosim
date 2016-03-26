package ecumene.exo.sim.abstractions.solar.io;

import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.ExoSolarObject;
import org.joml.Vector2f;

public class ExoSolarDefaults implements IExoSolarDefaults {
    public float  mass;
    public String name;
    public Vector2f position;
    public Vector2f velocity;

    public ExoSolarDefaults(){
        position = new Vector2f();
        velocity = new Vector2f();
        mass = 0;
        name = "Default Name";
    }

    @Override
    public ExoSolarObject fill(XMLSolarObject partialObject) {
        Vector2f position = new Vector2f(partialObject.position != null ? partialObject.position : getPosition());
        Vector2f velocity = new Vector2f();
        switch(partialObject.velocityType){
            case ORBIT: velocity = new Vector2f(partialObject.parentPosition).sub(new Vector2f(position)).perpendicular().normalize().mul(partialObject.velocity.length()); break;
            case SELF:  velocity = partialObject.velocity;
        }
        return new ExoSolarObject("HErro!!", partialObject.position, velocity, partialObject.mass);
    }

    public float getMass() {
        return mass;
    }

    public String getName() {
        return name;
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }
}
