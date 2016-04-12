package ecumene.exo.view.fbd;

import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.IExoPlanetObject;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.sim.common.physics.FreeBodyShape;
import ecumene.exo.sim.common.physics.dynamic.Force;
import ecumene.exo.sim.common.physics.instant.InsFBody;
import org.joml.Vector2f;

public class FreeBodyFactory {
    public static InsFBody createBody(ExoPlanetMap map, IExoPlanetObject planetObject){
        Force[] forceArr = new Force[map.getObjects().size()-1];
        int itr = 0;
        for(IExoPlanetObject object : map.getObjects()){
            if(object != planetObject){
                Vector2f distance = new Vector2f(object.getPosition().sub(planetObject.getPosition()));
                Vector2f force = distance.normalize().mul((map.G * planetObject.getMass() * object.getMass()) / (distance.length()*distance.length()));
                force.y *= -1;
                forceArr[itr] = (new Force("Fg>" + (itr + 1), force));
                itr++;
            }
        }
        return new InsFBody(FreeBodyShape.BALL, planetObject.getMass(), forceArr);
    }

    public static InsFBody createBody(ExoSolarMap map, IExoSolarObject solarObject){
        Force[] forceArr = new Force[map.getObjects().size()-1];
        int itr = 0;
        for(IExoSolarObject object : map.getObjects()){
            if(object != solarObject){
                Vector2f distance = new Vector2f(object.getPosition()).sub(new Vector2f(solarObject.getPosition()));
                Vector2f force = distance.normalize().mul((map.G * solarObject.getMass() * object.getMass()) / (distance.length()*distance.length()));
                force.y *= -1;
                forceArr[itr] = (new Force("Fg>" + (itr + 1), force));
                itr++;
            }
        }
        return new InsFBody(FreeBodyShape.BALL, solarObject.getMass(), forceArr);
    }

    public static InsFBody createBody(ExoGalaxyMap map, IExoGalaxyObject galaxyObject) {
        return new InsFBody(galaxyObject.getDynamicBody());
    }
}