package ecumene.exo.view.fbd;

import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FreeBodyFactory {
    public static FreeBody createBody(ExoSolarMap map, IExoSolarObject solarObject){
        Force[] forceArr = new Force[map.getObjects().size()-1];
        int itr = 0;
        for(IExoSolarObject object : map.getObjects()){
            if(object != solarObject){
                Vector2f distance = new Vector2f(object.getPosition().sub(solarObject.getPosition()));
                Vector2f force = distance.negate().normalize().mul((map.G * solarObject.getMass() * object.getMass()) / distance.length()*distance.length());
                forceArr[itr] = (new Force("Fg>" + (itr + 1), force));
                itr++;
            }
        }
        return new FreeBody(FreeBodyShape.BALL, solarObject.getMass(), forceArr);
    }

    public static FreeBody createBody(ExoGalaxyMap map, IExoGalaxyObject galaxyObject) {
        Vector2f distance = new Vector2f(galaxyObject.getPosition()).negate(); // Singularity is always at (0,0) ;)
        return new FreeBody(FreeBodyShape.BALL, galaxyObject.getMass(), new Force("Fg>galaxy-singularity", distance));
    }
}
