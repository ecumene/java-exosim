package ecumene.exo.sim.abstractions.solar;

import java.util.HashMap;
import java.util.List;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.DynamicRPoint;
import ecumene.exo.sim.common.physics.dynamic.FBody;
import ecumene.exo.sim.common.physics.dynamic.Gravity;
import org.joml.Vector2f;

import ecumene.exo.sim.common.map.real.RPoint;

public class ExoSolarObject extends DynamicRPoint implements IExoSolarObject {

	public HashMap<String, Double> materials;
	public ExoSolarMap             map;

	public ExoSolarObject(String name, float mass, Vector2f position, Vector2f startVelocity){
		super(name, position, new FBody(FreeBodyShape.BALL, mass));
		this.velocity = startVelocity;
		materials = new HashMap();
	}

	@Override
	public RPoint step(SimContext context, int steps, List<IExoSolarObject> objects) {
		for(int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).equals(this) && ((ExoSolarObject) objects.get(i)).collides(this))
				if (!(objects.get(i).getMass() > getMass())) {
					dynamics.mass += objects.get(i).getMass();
					float currentMag = getVelocity().length();
					this.velocity.normalize();
					this.velocity.mul(currentMag - objects.get(i).getVelocity().length() * (objects.get(i).getMass() / getMass()));
					objects.remove(i);
				}
		}
		onStep(context, steps);
		return this;
	}

	public void setMap(ExoSolarMap map){
		this.map = map;
		dynamics.getForces().clear();
		for(IExoSolarObject object : map.getObjects()){
			if(object != this){
				ExoSolarObject objectItr = ((ExoSolarObject) object);
				dynamics.getForces().add(new Gravity(this, objectItr, ExoSolarMap.GRAVITY));
			}
		}
	}

	public boolean collides(IExoSolarObject object2){
		float xDif = getPosition().x - object2.getPosition().x;
		float yDif = getPosition().y - object2.getPosition().y;
		return (xDif * xDif + yDif * yDif) < (getMass() + object2.getMass()) * (getMass() + object2.getMass());
	}
		
	@Override
	public String getName(int id) {
		return "Body #" + id;
	}

}
