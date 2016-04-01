package ecumene.exo.view.fbd;

import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.IExoPlanetObject;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import org.joml.Vector2f;

public class FreeBodyFactory {
    public static FreeBody createBody(ExoPlanetMap map, IExoPlanetObject planetObject){
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
        return new FreeBody(FreeBodyShape.BALL, planetObject.getMass(), forceArr);
    }

    public static FreeBody createBody(ExoSolarMap map, IExoSolarObject solarObject){
        Force[] forceArr = new Force[map.getObjects().size()-1];
        int itr = 0;
        for(IExoSolarObject object : map.getObjects()){
            if(object != solarObject){
                // TODO: Something here is weird...
                Vector2f distance = new Vector2f(object.getPosition()).sub(new Vector2f(solarObject.getPosition()));
                Vector2f force = distance.normalize().mul((map.G * solarObject.getMass() * object.getMass()) / (distance.length()*distance.length()));
                System.out.println(force.length());
                force.y *= -1;
                forceArr[itr] = (new Force("Fg>" + (itr + 1), force));
                itr++;
            }
        }
        return new FreeBody(FreeBodyShape.BALL, solarObject.getMass(), forceArr);
    }

    public static FreeBody createBody(ExoGalaxyMap map, IExoGalaxyObject galaxyObject) {
        Vector2f distance = new Vector2f(galaxyObject.getPosition()).negate(); // Singularity is always at (0,0) ;)
        distance.y *= -1;
        return new FreeBody(FreeBodyShape.BALL, galaxyObject.getMass(), new Force("Fg>galaxy-singularity", distance));
    }
}
